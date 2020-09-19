package com.adobe;

import org.omg.CORBA.INITIALIZE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import com.adobe.prj.dao.AdminDao;
import com.adobe.prj.entity.Admin;
import com.adobe.prj.entity.AdminDto;
import com.adobe.prj.service.AdminService;
import com.adobe.prj.service.BookingService;
import com.adobe.prj.service.RoomLayoutService;
import com.adobe.prj.service.RoomService;
import com.adobe.prj.util.InitializeApp;
import com.adobe.prj.dao.RoomLayoutDao;
import com.adobe.prj.entity.RoomLayout;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import com.adobe.prj.dao.AdminDao;
//import com.adobe.prj.entity.Admin;


@SpringBootApplication
public class MeetingroomApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(MeetingroomApplication.class, args);
	}
	

	
	@Override
	public void run(String... args) throws Exception {

		//initialization of admin and roomlayout in InitialzeApp class(util)

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
