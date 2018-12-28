package com.v;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class MyPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyPlatformApplication.class, args);
	}

}

