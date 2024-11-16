package com.electronic_ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.electronic_ecommerce"})
public class ElectronicEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicEcommerceApplication.class, args);
	}

}
