package com.adobe.prj.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adobe.prj.dao.AdminDao;
import com.adobe.prj.entity.Admin;
import com.adobe.prj.entity.AdminDto;

@Service
public class AdminService implements UserDetailsService {
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		
		
//		System.out.println(s);
		Admin admin = adminDao.findByEmail(s); 

		
		if(admin == null){
//			System.out.println("****");
			throw new UsernameNotFoundException("User not found with username: " + s);
		}
		
		System.out.println();
		
		return new org.springframework.security.core.userdetails.User(admin.getEmail(), admin.getPassword(),
				new ArrayList<>());
		
//		return new User("banu", "$2a$10$IAtCdzZYy7rDT6TUpAI16eaRtGDIFbpFgj/as0Z5xCR5pZ.WAAgfC", new ArrayList<>());
	}
	
	public Admin save(AdminDto admin){
		Admin newAdmin = new Admin();
		newAdmin.setEmail(admin.getEmail());
		newAdmin.setPassword(bcryptEncoder.encode(admin.getPassword()));
		return adminDao.save(newAdmin);
		
//		admin.setPassword(bcryptEncoder.encode(admin.getPassword()));
//		return adminDao.save(admin);

	}
	
}	

