package com.example.admin;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("biz-application")
@RequestMapping("/companies")
public interface CompanyService {

    @RequestMapping
    Resources<Company> findAll();

    @RequestMapping(method = RequestMethod.POST)
    void add(@RequestBody Company company);

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    void update(@PathVariable("id") Long id, @RequestBody Company company);

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    void delete(@PathVariable("id") Long id);

}
