package com.substring.quiz.category;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.TimeZone;

@SpringBootApplication
@EnableDiscoveryClient
public class CategoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoryServiceApplication.class, args);
	}

	@PostConstruct
	public void tz() {
		System.out.println(">>> JVM TIMEZONE = " + TimeZone.getDefault().getID());
	}
}
