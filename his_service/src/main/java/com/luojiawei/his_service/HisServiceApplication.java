package com.luojiawei.his_service;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.luojiawei.his_service")
@MapperScan("com.luojiawei.his_service.mapper") // 添加这行
@EnableFeignClients(basePackages = "com.luojiawei.api.client")
@EnableDiscoveryClient  // 开启服务发现
public class HisServiceApplication
{
    // 启动springboot
    public static void main(String[] args)
    {
        SpringApplication.run(HisServiceApplication.class, args);
    }
}
