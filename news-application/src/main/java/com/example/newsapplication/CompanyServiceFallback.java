package com.example.newsapplication;

import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author Alejandro Duarte.
 */
@Service
public class CompanyServiceFallback implements CompanyService {

    @Override
    public Resources<Company> findAll() {
        return new Resources<>(Arrays.asList(new Company("vaadin"), new Company("pivotal"), new Company("netflix")));
    }

}
