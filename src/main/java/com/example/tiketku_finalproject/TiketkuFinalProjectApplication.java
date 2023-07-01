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
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
<<<<<<< HEAD
						.allowedOrigins("*");
=======
						.allowedOrigins(
								"http://localhost:3000",
								"http://localhost:3001",
								"https://novel-tomatoes-production.up.railway.app",
								"*");
>>>>>>> cf7b15fe58d2269deb4ccaa547515209ba38ce22
			}
		};
	}

	@Bean
	public void sayHello() {
		System.out.println("hello world");
	}
}
