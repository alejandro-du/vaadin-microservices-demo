package com.example.newsapplication;

import com.vaadin.spring.server.SpringVaadinServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
public class NewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean<SpringVaadinServlet> springVaadinServlet(WebApplicationContext applicationContext) {
        SpringVaadinServlet servlet = new SpringVaadinServlet();
        servlet.setServiceUrlPath(applicationContext.getServletContext().getContextPath());
        ServletRegistrationBean<SpringVaadinServlet> registrationBean = new ServletRegistrationBean<>(servlet, "/*");
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }

}
