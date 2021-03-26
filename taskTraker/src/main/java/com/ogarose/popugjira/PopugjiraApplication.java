package com.ogarose.popugjira;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PopugjiraApplication {

	public static void main(String[] args) {
		SpringApplication.run(PopugjiraApplication.class, args);
	}

}
