package com.shahidfoy.rediscachedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RedisCacheDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisCacheDemoApplication.class, args);
	}
}
