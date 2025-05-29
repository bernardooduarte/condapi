package com.example.condapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.condapi.model.entity")
public class CondapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CondapiApplication.class, args);
	}

}
