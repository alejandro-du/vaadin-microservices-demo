package com.example.admin;

import com.vaadin.ui.Notification;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author Alejandro Duarte.
 */
@Service
public class CompanyServiceFallback implements CompanyService {

    @Override
    public Resources<Company> findAll() {
        Notification.show("Cannot connect to biz-application. Please try again later.", Notification.Type.ERROR_MESSAGE);
        return new Resources<>(Collections.emptyList());
    }

    @Override
    public void add(Company company) {
    }

    @Override
    public void update(Long id, Company company) {
    }

    @Override
    public void delete(Long id) {
    }

}
