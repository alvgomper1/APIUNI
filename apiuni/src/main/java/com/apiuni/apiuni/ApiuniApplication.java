package com.apiuni.apiuni;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class ApiuniApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(ApiuniApplication.class, args);
		 	}

}
