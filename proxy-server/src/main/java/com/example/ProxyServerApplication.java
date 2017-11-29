package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableZuulProxy
public class ProxyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyServerApplication.class, args);
    }

}
