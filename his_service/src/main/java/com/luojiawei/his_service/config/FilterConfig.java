package com.luojiawei.his_service.config;

import com.luojiawei.his_service.filter.RefreshTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {
    private final RefreshTokenFilter refreshTokenFilter;

//    @Bean
//    public FilterRegistrationBean<RefreshTokenFilter> refreshTokenFilterBean() {
//        FilterRegistrationBean<RefreshTokenFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(refreshTokenFilter); // 这里使用已经注入的 Bean
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setOrder(0); // 设置过滤器的顺序
//        return registrationBean;
//    }
}