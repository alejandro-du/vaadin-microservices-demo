package com.example.admin;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("biz-application")
public interface CompanyService {

    @RequestMapping("/companies")
    Resources<Company> list();

}
