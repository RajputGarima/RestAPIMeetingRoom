package com.adobe.prj.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adobe.prj.dao.AdminDao;
import com.adobe.prj.dao.RoomLayoutDao;
import com.adobe.prj.entity.Admin;
import com.adobe.prj.entity.AdminDto;
import com.adobe.prj.entity.RoomLayout;
import com.adobe.prj.service.AdminService;

@Component
public class InitializeApp {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private RoomLayoutDao roomLayoutDao;
	
	@PostConstruct
	public void init() {
		
		// Adding the first admin
		AdminDto admin= new AdminDto();
		admin.setEmail("admin@admin.com");
		admin.setPassword("admin");
		Admin newadmin = adminDao.findByEmail(admin.getEmail());
		if(newadmin == null) adminService.save(admin);			

		//	Default Layout
		if(roomLayoutDao.findByTitle("Classroom")==null) {
		RoomLayout roomLayout = new RoomLayout();
		roomLayout.setImageUrl("classroom_image");
		roomLayout.setTitle("Classroom");
		roomLayoutDao.save(roomLayout);
		}
		
	}

}
