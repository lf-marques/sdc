package com.sdc.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SdcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SdcApplication.class, args);
	}

}
