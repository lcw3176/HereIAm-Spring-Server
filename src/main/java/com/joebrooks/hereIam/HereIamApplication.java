package com.joebrooks.hereIam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("file:d:/key/hereiam.properties")
public class HereIamApplication {

	public static void main(String[] args) {
		SpringApplication.run(HereIamApplication.class, args);
	}

}
