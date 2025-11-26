package com.substring.quiz.category;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class CategoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoryServiceApplication.class, args);
	}

	@PostConstruct
	public void tz() {
		System.out.println(">>> JVM TIMEZONE = " + TimeZone.getDefault().getID());
	}
}
