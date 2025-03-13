package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.example.demo")
@EnableFeignClients(basePackages = "org.example.client") // 指定Feign客户端所在包
@EnableDiscoveryClient  // 开启服务发现
public class ModelGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModelGatewayApplication.class, args);
    }
}