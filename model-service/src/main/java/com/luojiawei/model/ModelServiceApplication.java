package com.luojiawei.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.luojiawei.model")
@EnableFeignClients(basePackages = "com.luojiawei.api.client") // 指定Feign客户端所在包
@EnableDiscoveryClient  // 开启服务发现
public class ModelServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModelServiceApplication.class, args);
    }
}