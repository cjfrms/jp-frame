package com.jptech.jpframe.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class JpCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpCenterApplication.class, args);
	}
}
