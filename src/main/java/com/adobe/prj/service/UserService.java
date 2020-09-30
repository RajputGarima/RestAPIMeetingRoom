package com.adobe.prj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.adobe.prj.dao.BookingDao;
import com.adobe.prj.dao.UserDao;
import com.adobe.prj.entity.Booking;
import com.adobe.prj.entity.User;
import com.adobe.prj.exception.CustomException;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BookingDao bookingDao;
	
	public List<User> getUsers(){
		return userDao.findAll();
	}
	
	public User getUser(int id) {
		return userDao.findById(id).get(); 
	}
	
	public User addUser(User b) {
		User u = null;
		try {
			u = userDao.save(b);
		}catch(DataIntegrityViolationException exp) {
			// unique constraint
		        throw new CustomException("integrity violation SQL " + exp.getMostSpecificCause());
		}
		catch(javax.validation.ConstraintViolationException exp) {
			// @Min, @NotNULL
			throw new CustomException("constraint violation - name -  " + exp.getConstraintViolations() );
		}
		return u;
	}
	
	public void deleteUser(int id) {
		
		List<Booking> bookings = bookingDao.getByUserId(id);
		
		for(Booking b: bookings) {
			bookingDao.delete(b);
		}
				
		User u = userDao.findById(id).get(); 
		userDao.delete(u);
	}
	
	
}
