package com.example.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyService {

    private final JdbcTemplate jdbc;

    @Autowired
    public CompanyService(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void add(@RequestParam(name="name") String name) {
        jdbc.update("INSERT INTO company(name) VALUES (?)", name);
    }

}
