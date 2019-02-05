package com.example.newsapplication;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "${biz-application.name:null}", url = "${biz-application.url:}", fallback = CompanyServiceFallback.class)
@Primary
public interface CompanyService {

    @RequestMapping("/companies")
    Resources<Company> findAll();

}
