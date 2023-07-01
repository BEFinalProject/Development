package com.example.tiketku_finalproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class TiketkuFinalProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiketkuFinalProjectApplication.class, args);
	}

	@Value("${allowed.origin}")
	private String allowedOrigin;

	@Bean
	public WebMvcConfigurer corsConfig() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*");
			}
		};
	}

	@Bean
	public void sayHello() {
		System.out.println("hello world");
	}
}
