package com.adobe.prj.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
	
	public Optional<Booking> getBooking(int id){
		return bookingDao.findById(id);
	}
	
	public Booking addBooking(Booking b) {
		Room r = b.getRoom();
		RoomLayout l = b.getRoomLayout();
		
		b.setRoom(roomDao.findById(r.getId()).get());
		b.setRoomLayout(roomLayoutDao.findById(l.getId()).get());
		return bookingDao.save(b);
	}
		
	public void deleteBooking(int id) {
		Booking b = bookingDao.findById(id).get(); 
		bookingDao.delete(b);
	}
	
	public List<Booking> getByUserId(int id){
		return bookingDao.getByUserId(id);
	}
	
	public List<Booking> getUpcomingBookings(LocalDate d){
		return bookingDao.getUpcomingBookings(d);
	}

	public List<Booking> getBookingByDate(String date) {
		return bookingDao.getBookingByDate(date);
	}
	
	public Long getBookingsCount() {
		return new Long(bookingDao.count());
	}
	
	public Long getBookingsCountByDate() {
		return new Long(bookingDao.getBookingsCountByDate(LocalDate.now()));
	}

	public Long getBookingsCountMadeToday() {
		return new Long(bookingDao.getBookingsCountMadeToday(LocalDate.now()));
	}

}
