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

    public static final String ERROR_MESSAGE = "Cannot connect to biz-application. Please try again later.";

    @Override
    public Resources<Company> findAll() {
        return new Resources<Company>(Collections.emptyList());
    }

    @Override
    public void add(Company company) {
        Notification.show(ERROR_MESSAGE, Notification.Type.ERROR_MESSAGE);
    }

    @Override
    public void update(Long id, Company company) {
        Notification.show(ERROR_MESSAGE, Notification.Type.ERROR_MESSAGE);
    }

    @Override
    public void delete(Long id) {
        Notification.show(ERROR_MESSAGE, Notification.Type.ERROR_MESSAGE);
    }

}
