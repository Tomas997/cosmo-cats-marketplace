package com.example.cosmocatsmarketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CosmocatsmarketplaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CosmocatsmarketplaceApplication.class, args);
	}

}
