package com.example.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
public class BizApplication {

    public static void main(String... args) {
        SpringApplication.run(BizApplication.class, args);
    }

    @Autowired
    public BizApplication(RepositoryRestConfiguration repositoryRestConfiguration) {
        repositoryRestConfiguration.exposeIdsFor(Company.class);
    }

}
