package com.shipogle.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ShipogleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShipogleApplication.class, args);
	}

	@GetMapping("/hello")
	String helloworld(){
		return "Application running";
	}
}
