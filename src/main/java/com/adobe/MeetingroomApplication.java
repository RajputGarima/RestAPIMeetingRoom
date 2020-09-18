package com.adobe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.adobe.prj.dao.RoomLayoutDao;
import com.adobe.prj.entity.RoomLayout;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//import com.adobe.prj.dao.AdminDao;
//import com.adobe.prj.entity.Admin;


@SpringBootApplication
public class MeetingroomApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(MeetingroomApplication.class, args);
	}
	
	@Autowired
	RoomLayoutDao roomLayoutDao;
	
	@Override
	public void run(String... args) throws Exception {
//		Default Layout
		if(roomLayoutDao.findByTitle("Classroom").equals(null)) {
		RoomLayout roomLayout = new RoomLayout();
		roomLayout.setImageUrl("classroom_image");
		roomLayout.setTitle("Classroom");
		roomLayoutDao.save(roomLayout);
		}
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
