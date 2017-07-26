package com.stackroute.swisit.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
@EnableConfigServer
@SpringBootApplication
public class SpringFinalConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringFinalConfigApplication.class, args);
	}
}
