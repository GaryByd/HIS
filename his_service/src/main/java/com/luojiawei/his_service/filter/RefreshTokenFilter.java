// RefreshTokenFilter.java
package com.luojiawei.his_service.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import com.luojiawei.common.domain.UserDTO;
import com.luojiawei.common.utils.RedisConstants;
import com.luojiawei.common.utils.UserHolder;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("authorization");
        if (StrUtil.isBlank(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        String key = RedisConstants.LOGIN_USER_KEY + token;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(key);
        if (userMap.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        JWT jwt = JWTUtil.parseToken(token);
        try {
            // 验证token
            JWTValidator.of(jwt).validateDate();
        } catch(Exception e) {
            filterChain.doFilter(request, response);
            return;
        }
        UserDTO userDTO = UserDTO.parse(userMap.toString());
        UserHolder.saveUser(userDTO);
        try {
            stringRedisTemplate.expire(key, RedisConstants.LOGIN_USER_TTL, TimeUnit.MINUTES);
        } catch (Exception e) {
            response.getWriter().write("Internal Server Error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        filterChain.doFilter(request, response);
    }
}