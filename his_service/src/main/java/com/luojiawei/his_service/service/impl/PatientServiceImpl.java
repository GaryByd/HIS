package com.luojiawei.his_service.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import com.luojiawei.common.domain.dto.AuthDTO;
import com.luojiawei.common.domain.dto.Result;
import com.luojiawei.common.domain.dto.UserDTO;
import com.luojiawei.common.domain.po.Patient;
import com.luojiawei.common.domain.vo.AuthVo;
import com.luojiawei.common.domain.vo.LoginVo;
import com.luojiawei.common.utils.UserHolder;
import com.luojiawei.his_service.mapper.PatientMapper;
import com.luojiawei.his_service.service.IPatientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luojiawei.common.utils.JwtUtil;
import com.luojiawei.his_service.utils.VerifyUtils;
import com.luojiawei.common.utils.WeiChatUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.luojiawei.common.utils.RedisConstants.LOGIN_USER_KEY;
import static com.luojiawei.common.utils.RedisConstants.LOGIN_USER_TTL;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 罗佳炜
 * @since 2025-03-01
 */
@Service
@RequiredArgsConstructor
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements IPatientService {
    // 配置连接池
    private static HikariDataSource dataSource;
    private final StringRedisTemplate stringRedisTemplate;
    private final PatientMapper patientMapper;

    static {
        // 连接池配置
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://111.230.32.147:3306/yygh_user");
        config.setUsername("root");
        config.setPassword("4+K/D2zvxHooBhn5begcMQz/04IpAMpq");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // 连接池调优参数（根据实际情况调整）
        config.setMaximumPoolSize(10);      // 最大连接数
        config.setMinimumIdle(5);          // 最小空闲连接
        config.setConnectionTimeout(30000); // 连接超时时间(毫秒)
        config.setIdleTimeout(600000);     // 空闲连接超时时间(毫秒)
        config.setMaxLifetime(1800000);    // 连接最大存活时间(毫秒)

        // 其他优化配置
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    @Override
    public Result<LoginVo> login(String code, HttpSession session) {
        //解析code里面的code数据 json
        String code_js = (String) JSONUtil.parse(code).getByPath("code");
        // 1. 使用 code 通过 WeiChatUtil 获取 openId
        String openId = WeiChatUtil.getSessionId(code_js);
//        //假设openid
        openId = "owhoa7fITbB2gA1N4dxwWmjN8Xsw";
        // 2. 检查 openId 是否有效
        if (openId == null || openId.isEmpty()) {
            return Result.fail(401, "无效的微信code，无法获取openId");
        }
        // 3. 根据 openId 查询用户信息
        UserDTO userDTO = patientMapper.selectIdByOpenId(openId);
        //UUID token
        String tokenId = JwtUtil.getUUID();
        // 4. 用户存在则登录不存在则注册
        if (userDTO == null) {
            Patient patient = new Patient();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String timestamp = sdf.format(new Date());
            patient.setName("用户" + timestamp);
            patient.setOpenId(openId);
            patientMapper.insert(patient);
        }
        // 5. 生成 token
        String token = JwtUtil.createJWT(tokenId, openId, 2592000000L);
        // 6. 创建线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            // 7. 将用户信息保存到 Redis 中
            Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                    CopyOptions.create().setIgnoreNullValue(true)
                            .setFieldValueEditor((key, value) -> value != null ? value.toString() : ""));
            stringRedisTemplate.opsForHash().putAll(LOGIN_USER_KEY + token, userMap);
            // 8. 设置 token 过期时间
            stringRedisTemplate.expire(LOGIN_USER_KEY + token, LOGIN_USER_TTL, TimeUnit.MINUTES);
            // 9. 将 token 保存到 session 中
            session.setAttribute("user", userDTO);
            executorService.shutdown();
        });
        // 关闭线程池
        // 10. 返回结果
        return Result.ok("Success", new LoginVo(token, userDTO));
    }

    @Override
    public Result<Object> Verify(AuthDTO authDTO, HttpSession session) {
        //提取authedVO中的用户信息
        String name = authDTO.getName();
        String idCard = authDTO.getIdCard();
        String phone = authDTO.getPhone();
        //根据phone查询用户信息
        UserDTO user = UserHolder.getUser();

        if (user == null) {
            return Result.fail("用户不存在");
        }
        //如果用户已经验证过了
        if (user.getVerified()) {
            return Result.fail(403, "该用户已经验证过了");
        }
        Patient patient = patientMapper.selectById(user.getId());
        try {
            String responseBody = VerifyUtils.verifyIdentity(name, idCard);

            // 解析responseBody里面的respCode
            if (!responseBody.contains("respCode")) {
                return Result.fail("请求错误");
            }

            // 使用JSONUtil解析整个响应
            JSONObject data = JSONUtil.parseObj(responseBody);
            String respCode = data.getStr("respCode");
            if (!respCode.equals("0000")) {
                AuthVo authVo = JSONUtil.toBean(responseBody, AuthVo.class);
                return Result.ok("Fail", authVo);
            }
            String certificatesNo = idCard; // 要查询的身份证号
            // 使用try-with-resources自动关闭资源
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "SELECT * FROM patient WHERE certificates_no = ?")) {

                stmt.setString(1, certificatesNo);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) { // 移动游标到第一行
                        int hospitalId = rs.getInt("id");
                        System.out.println("ID: " + hospitalId);
                        patient.setHospitalPid(String.valueOf(hospitalId));
                    } else {
                        // 如果结果集中没有数据，可以根据业务逻辑处理
                        throw new RuntimeException("未找到匹配的记录！");
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("数据库操作错误！"+e);
            }
            // 提取data中的字段并设置到patient对象
            patient.setVerified(1);
            patient.setName(data.getStr("name")); // 设置姓名
            patient.setIdCard(data.getStr("idNo")); // 设置身份证号
            patient.setProvince(data.getStr("province")); // 设置省份
            patient.setCity(data.getStr("city")); // 设置城市
            patient.setCounty(data.getStr("county")); // 设置区县
            patient.setBirthday(data.getStr("birthday")); // 设置生日
            patient.setSex("M".equals(data.getStr("sex")) ? "男" : "女");
            patient.setAge(Integer.parseInt(data.getStr("age"))); // 设置年龄
            // 更新数据库
            patientMapper.updateById(patient);

            // 将responseBody转换为authVo
            AuthVo authVo = JSONUtil.toBean(responseBody, AuthVo.class);
            return Result.ok("Success", authVo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
