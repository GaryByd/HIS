package com.luojiawei.his_service.config;

import com.luojiawei.his_service.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
//                .excludePathPatterns("/patient/auth/login")
                .excludePathPatterns("/**")
                .order(1); // 设置拦截器的顺序
    }
}