package com.imagen_social.mac_morelos_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.imagen_social.mac_morelos_api.models")
public class MacMorelosApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MacMorelosApiApplication.class, args);
	}

}
