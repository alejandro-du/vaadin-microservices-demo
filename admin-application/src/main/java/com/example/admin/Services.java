package com.example.admin;

import com.vaadin.spring.server.SpringVaadinServlet;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * @author Alejandro Duarte.
 */
@Service
public class Services {

    public static CompanyService getCompanyService() {
        return getApplicationContext().getBean(CompanyService.class);
    }

    public static ApplicationContext getApplicationContext() {
        ServletContext servletContext = SpringVaadinServlet.getCurrent().getServletContext();
        return WebApplicationContextUtils.getWebApplicationContext(servletContext);
    }

}
