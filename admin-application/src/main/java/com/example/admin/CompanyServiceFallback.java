package com.example.admin;

import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

/**
 * @author Alejandro Duarte.
 */
@Service
public class CompanyServiceFallback implements CompanyService {

    @Override
    public Resources<Company> findAll() {
        return null;
    }

    @Override
    public Company add(Company company) {
        return null;
    }

    @Override
    public Company update(Long id, Company company) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }

}
