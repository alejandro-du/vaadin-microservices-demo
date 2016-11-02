package com.example.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserService {

    private final JdbcTemplate jdbc;

    @Autowired
    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

}
