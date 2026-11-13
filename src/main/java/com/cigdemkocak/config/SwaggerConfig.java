package com.cigdemkocak.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
	    return new OpenAPI()
	            .info(new Info()
	                    .title("Car Dealer Management API")
	                    .version("1.0.0")
	                    .description("""
	                            REST API for managing car dealer operations: customers, vehicles and sales transactions.

	                            **Features**
	                            - JWT-based authentication (register, login, refresh token)
	                            - Customer, vehicle and dealer management
	                            - Car sales with real-time USD exchange rates from the Central Bank of Turkey (TCMB)

	                            **How to try it**
	                            1. Create a user with `/register`
	                            2. Log in with `/authenticate` and copy the access token
	                            3. Click the **Authorize** button (top right) and paste the token
	                            4. Try the protected endpoints

	                            **Tech stack:** Java 17, Spring Boot, Spring Security, Spring Data JPA, PostgreSQL, Docker
	                            """)
	                    .contact(new Contact()
	                            .name("Çiğdem Koçak")
	                            .url("https://github.com/cigdemkocak")
	                            .email("kocakcigdem9@gmail.com")))
	            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
	            .components(new Components().addSecuritySchemes("bearerAuth",
	                    new SecurityScheme()
	                            .type(SecurityScheme.Type.HTTP)
	                            .scheme("bearer")
	                            .bearerFormat("JWT")
	                            .description("Paste the JWT access token returned by the login endpoint (do not add the 'Bearer' prefix).")));
	}
}
