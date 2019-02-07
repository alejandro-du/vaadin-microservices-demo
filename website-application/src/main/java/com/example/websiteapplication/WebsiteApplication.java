package com.example.websiteapplication;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapAttributeConfig;
import com.hazelcast.config.MapIndexConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.vaadin.flow.function.DeploymentConfiguration;
import com.vaadin.flow.server.*;
import com.vaadin.flow.spring.SpringServlet;
import com.vaadin.flow.spring.SpringVaadinServletService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.session.hazelcast.HazelcastSessionRepository;
import org.springframework.session.hazelcast.PrincipalNameExtractor;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHazelcastHttpSession
@Controller
public class WebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsiteApplication.class, args);
    }

    @RequestMapping(value = "/")
    public String forward() {
        return "forward:/website/";
    }

    @Bean
    public ServletRegistrationBean<SpringServlet> springServlet(ApplicationContext applicationContext,
            @Value("${vaadin.urlMapping}") String vaadinUrlMapping) {

        SpringServlet servlet = buildSpringServlet(applicationContext);
        ServletRegistrationBean<SpringServlet> registrationBean =
                new ServletRegistrationBean<>(servlet, vaadinUrlMapping, "/frontend/*");
        registrationBean.setLoadOnStartup(1);
        registrationBean.addInitParameter(Constants.SERVLET_PARAMETER_SYNC_ID_CHECK, "false");
        return registrationBean;
    }

    private SpringServlet buildSpringServlet(ApplicationContext applicationContext) {
        return new SpringServlet(applicationContext) {
            @Override
            protected VaadinServletService createServletService(DeploymentConfiguration deploymentConfiguration) throws
                    ServiceException {
                SpringVaadinServletService service =
                        buildSpringVaadinServletService(this, deploymentConfiguration, applicationContext);
                service.init();
                return service;
            }
        };
    }

    private SpringVaadinServletService buildSpringVaadinServletService(SpringServlet servlet,
                                                                       DeploymentConfiguration deploymentConfiguration,
                                                                       ApplicationContext applicationContext) {
        return new SpringVaadinServletService(servlet, deploymentConfiguration, applicationContext) {
            @Override
            public void requestEnd(VaadinRequest request, VaadinResponse response, VaadinSession session) {
                if (session != null) {
                    try {
                        session.lock();
                        writeToHttpSession(request.getWrappedSession(), session);
                    } finally {
                        session.unlock();
                    }
                }
                super.requestEnd(request, response, session);
            }
        };
    }

    @Bean
    public HazelcastInstance hazelcastInstance(
            @Value("${hazelcast.max.no.heartbeat.seconds:60}") String hazelcastHeartbeat) {

        MapAttributeConfig attributeConfig =
                new MapAttributeConfig().setName(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
                        .setExtractor(PrincipalNameExtractor.class.getName());

        Config config = new Config();
        config.setProperty("hazelcast.max.no.heartbeat.seconds", hazelcastHeartbeat)
                .getMapConfig("spring:session:sessions")
                .addMapAttributeConfig(attributeConfig)
                .addMapIndexConfig(new MapIndexConfig(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE, false));
        config.getGroupConfig().setName("website");

        return Hazelcast.newHazelcastInstance(config);
    }

}
