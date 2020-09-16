package com.adobe.prj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adobe.prj.dao.BookingDao;
import com.adobe.prj.dao.RoomDao;
import com.adobe.prj.dao.RoomLayoutDao;
import com.adobe.prj.entity.Booking;
import com.adobe.prj.entity.Room;
import com.adobe.prj.entity.RoomLayout;

@Service
public class BookingService {
	
	@Autowired
	private BookingDao bookingDao;
	
	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private RoomLayoutDao roomLayoutDao;
	
	public List<Booking> getBookings(){
		return bookingDao.findAll();
	}
	
	public Booking getBooking(int id) {
		return bookingDao.findById(id).get(); 
	}
	
	public Booking addBooking(Booking b) {
		Room r = b.getRoom();
		RoomLayout l = b.getRoomLayout();
		
		b.setRoom(roomDao.findById(r.getRoomId()).get());
		b.setRoomLayout(roomLayoutDao.findById(l.getLayoutId()).get());
		return bookingDao.save(b);
	}
		
	public void deleteBooking(int id) {
		Booking b = bookingDao.findById(id).get(); 
		bookingDao.delete(b);
	}
	
	public List<Booking> getByUserId(int id){
		return bookingDao.getByUserId(id);
	}
	
}
