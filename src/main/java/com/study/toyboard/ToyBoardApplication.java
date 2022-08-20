package com.study.toyboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class ToyBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToyBoardApplication.class, args);
	}

}
