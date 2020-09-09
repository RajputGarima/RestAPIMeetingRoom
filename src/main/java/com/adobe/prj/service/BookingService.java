package com.adobe.prj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adobe.prj.dao.BookingDao;
import com.adobe.prj.entity.Booking;

@Service
public class BookingService {
	
	@Autowired
	private BookingDao bookingDao;
	
	public List<Booking> getBookings(){
		return bookingDao.findAll();
	}
	
	public Booking getBooking(int id) {
		return bookingDao.findById(id).get(); 
	}
	
	public Booking addBooking(Booking b) {
		return bookingDao.save(b);
	}
		
}
