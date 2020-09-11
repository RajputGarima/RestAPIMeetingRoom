package com.adobe.prj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adobe.prj.dao.BookingDao;
import com.adobe.prj.dao.UserDao;
import com.adobe.prj.entity.Booking;
import com.adobe.prj.entity.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BookingDao bookingDao;
	
	public List<User> getUsers(){
		return userDao.findAll();
	}
	
	public User getUser(String id) {
		return userDao.findById(id).get(); 
	}
	
	public User addUser(User b) {
		return userDao.save(b);
	}
	
	public void deleteUser(String id) {
		
		List<Booking> bookings = bookingDao.getByEmail(id);
		
		for(Booking b: bookings) {
			bookingDao.delete(b);
		}
				
		User u = userDao.findById(id).get(); 
		userDao.delete(u);
	}
	
	
}
