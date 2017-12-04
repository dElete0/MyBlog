package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class MyBlogApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(MyBlogApplication.class, args);
	}
}
