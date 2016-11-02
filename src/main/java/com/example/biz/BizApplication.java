package com.example.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BizApplication {

    public static void main(String... args) {
        System.setProperty("spring.config.name", "biz-application");
        SpringApplication.run(BizApplication.class, args);
    }

}
