package com.example.websiteapplication;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapAttributeConfig;
import com.hazelcast.config.MapIndexConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.vaadin.spring.server.SpringVaadinServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.session.hazelcast.HazelcastSessionRepository;
import org.springframework.session.hazelcast.PrincipalNameExtractor;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHazelcastHttpSession
public class WebsiteApplication {

	@Value("${hazelcast.max.no.heartbeat.seconds:60}")
	private String hazelcastHearbeat;

	public static void main(String[] args) {
		SpringApplication.run(WebsiteApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean<SpringVaadinServlet> springVaadinServlet(WebApplicationContext applicationContext) {
		SpringVaadinServlet servlet = new SpringVaadinServlet();
		servlet.setServiceUrlPath(applicationContext.getServletContext().getContextPath());
		ServletRegistrationBean<SpringVaadinServlet> registrationBean = new ServletRegistrationBean<>(servlet, "/*");
		registrationBean.setLoadOnStartup(1);
		return registrationBean;
	}

	@Bean
	public HazelcastInstance hazelcastInstance() {
		MapAttributeConfig attributeConfig = new MapAttributeConfig()
				.setName(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
				.setExtractor(PrincipalNameExtractor.class.getName());

		Config config = new Config();
		config.setProperty("hazelcast.max.no.heartbeat.seconds", hazelcastHearbeat)
				.getMapConfig("spring:session:sessions:website-application")
				.addMapAttributeConfig(attributeConfig)
				.addMapIndexConfig(new MapIndexConfig(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE, false));

		return Hazelcast.newHazelcastInstance(config);
	}

}
