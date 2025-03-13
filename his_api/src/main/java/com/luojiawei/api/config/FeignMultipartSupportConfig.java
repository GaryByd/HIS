package com.luojiawei.api.config;

import feign.Logger;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FeignMultipartSupportConfig {
    @Bean
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(new ObjectFactory<>() {
            @Override
            public HttpMessageConverters getObject() {
                return new HttpMessageConverters(
                    new RestTemplate().getMessageConverters()
                );
            }
        }));
    }
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL; // 开启详细日志
    }
}
