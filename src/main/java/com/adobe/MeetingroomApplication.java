package com.adobe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.adobe.prj.dao.AdminDao;
import com.adobe.prj.entity.Admin;

@SpringBootApplication
public class MeetingroomApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(MeetingroomApplication.class, args);
	}
	
	
	@Override
	public void run(String... args) throws Exception {
			
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE");
			}
		};
	}
}
