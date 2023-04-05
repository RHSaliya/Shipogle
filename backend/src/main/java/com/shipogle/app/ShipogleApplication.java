package com.shipogle.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@RestController
@EnableWebSocket
public class ShipogleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShipogleApplication.class, args);
	}

	@GetMapping("/")
	String home(){
		return "Application running";
	}
}
