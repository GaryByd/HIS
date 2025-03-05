package com.luojiawei.his_service.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONUtil;
import com.luojiawei.his_service.domain.dto.AuthDTO;
import com.luojiawei.his_service.domain.dto.Result;
import com.luojiawei.his_service.domain.dto.UserDTO;
import com.luojiawei.his_service.domain.po.Patient;
import com.luojiawei.his_service.domain.vo.AuthVo;
import com.luojiawei.his_service.domain.vo.LoginVo;
import com.luojiawei.his_service.mapper.PatientMapper;
import com.luojiawei.his_service.service.IPatientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luojiawei.common.utils.JwtUtil;
import com.luojiawei.his_service.utils.VerifyUtils;
import com.luojiawei.common.utils.WeiChatUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
 *  服务实现类
 * </p>
 *
 * @author 罗佳炜
 * @since 2025-03-01
 */
@Service
@RequiredArgsConstructor
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements IPatientService {


    private final StringRedisTemplate stringRedisTemplate;
    private final PatientMapper patientMapper;
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
            patient.setName("用户"+timestamp);
            patient.setOpenId(openId);
            patientMapper.insert(patient);
        }
        // 5. 生成 token
        String token = JwtUtil.createJWT(tokenId,openId,2592000000L);
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
        return Result.ok("Success",new LoginVo(token, userDTO));
    }

    @Override
    public Result<Object> Verify(AuthDTO authDTO, HttpSession session) {
        //提取authedVO中的用户信息
        String name = authDTO.getName();
        String idCard = authDTO.getIdCard();
        String phone = authDTO.getPhone();
        //根据phone查询用户信息
        Patient patient = patientMapper.selectByPhone(phone);
        if (patient == null) {
            return Result.fail("用户不存在");
        }
        //如果用户已经验证过了
        if (patient.getVerified()==1) {
            return Result.fail(403,"该用户已经验证过了");
        }
        try {
            String responseBody = VerifyUtils.verifyIdentity(name, idCard);
            //解析responseBody里面的respCode
            if (!responseBody.contains("respCode")) {
                return Result.fail("请求错误");
            }
            String respCode = (String) JSONUtil.parse(responseBody).getByPath("respCode");
            if (!respCode.equals("0000")) {
                AuthVo authVo = JSONUtil.toBean(responseBody, AuthVo.class);
                return Result.ok("Fail", authVo);
            }
            patient.setVerified(1);
            patientMapper.updateById(patient);
            //将responseBody转换为authVo
            AuthVo authVo = JSONUtil.toBean(responseBody, AuthVo.class);
            return Result.ok("Success", authVo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}
