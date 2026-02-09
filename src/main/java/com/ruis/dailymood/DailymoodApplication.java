package com.ruis.dailymood;

import com.ruis.dailymood.microservices.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DailymoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailymoodApplication.class, args);

	}

}
