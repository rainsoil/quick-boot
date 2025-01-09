package com.su60.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class QuickBootSecurityExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickBootSecurityExampleApplication.class, args);
	}

}
