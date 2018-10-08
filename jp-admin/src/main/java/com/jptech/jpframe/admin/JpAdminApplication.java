package com.jptech.jpframe.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
public class JpAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpAdminApplication.class, args);
	}

}
