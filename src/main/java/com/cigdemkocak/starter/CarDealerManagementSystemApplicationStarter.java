package com.cigdemkocak.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.cigdemkocak"})
@EntityScan(basePackages = {"com.cigdemkocak"})
@EnableJpaRepositories(basePackages = {"com.cigdemkocak"})
@SpringBootApplication
public class CarDealerManagementSystemApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(CarDealerManagementSystemApplicationStarter.class, args);
	}

}
