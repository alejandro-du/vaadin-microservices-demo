package com.example.admin;

import org.springframework.stereotype.Service;

/**
 * @author Alejandro Duarte.
 */
@Service
public class Services {

    private static CompanyService companyService;

    public Services(CompanyService companyService) {
        this.companyService = companyService;
    }

    public static CompanyService getCompanyService() {
        return companyService;
    }

}
