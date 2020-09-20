package com.adobe.prj.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import com.adobe.prj.dao.BookingDao;
import com.adobe.prj.dao.RoomDao;
import com.adobe.prj.dao.RoomLayoutDao;
import com.adobe.prj.entity.Booking;
import com.adobe.prj.entity.Food;
import com.adobe.prj.entity.Room;
import com.adobe.prj.entity.RoomLayout;

import com.adobe.prj.exception.CustomException;

import com.adobe.prj.exception.ExceptionNotFound;


@Service
public class BookingService {
	
	@Autowired
	private BookingDao bookingDao;
	
	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private RoomLayoutDao roomLayoutDao;
	
	@Autowired 
	private RoomService roomService;
	
	public List<Booking> getBookings(){
		return bookingDao.findAll();
	}
	
	public Optional<Booking> getBooking(int id){
		return bookingDao.findById(id);
	}
	
	@Transactional
	public Booking addBooking(Booking b) {
//		Entities extracted from booking
		Room r = b.getRoom();
		RoomLayout l = b.getRoomLayout();

		Optional<Room> room = roomDao.findById(r.getId());
		
//		Entities fetched from database
		Room R = room.get();
		int rlId = l.getId();
		List<RoomLayout> rl = R.getRoomLayouts();
		boolean flag = true;
		
		for(RoomLayout i : rl) {
			int x = i.getId();
			if(x == rlId) {
				flag = false;
				break;
			}
		}
		if(flag)
			throw new CustomException("Selected layout is not applicable to the selected room");
//			return bookingDao.save(b);

//		b.setRoom(roomDao.findById(r.getId()).get());
//		b.setRoomLayout(roomLayoutDao.findById(l.getId()).get());
		Booking booking = null;
		try {
			booking = bookingDao.save(b);
		}
		catch(javax.validation.ConstraintViolationException exp) {
			// @Min, @NotNULL
			throw new CustomException("constraint violation - name -  " + exp.getConstraintViolations() );
		}
		// updating no of bookings for the booked room 
		Room newroom = booking.getRoom();
		newroom = roomService.getRoom(newroom.getId()).get();
		newroom.setBookings(roomService.getFutureBookingsById(newroom.getId()));
		roomService.updateRoom(newroom.getId(),newroom);
		
		return booking;

	}
		
	public void deleteBooking(int id) {
		Booking b = bookingDao.findById(id).get(); 
		
		Room newroom = b.getRoom();
		
		bookingDao.delete(b);
		
		//updating no of bookings for the booked room
		newroom = roomService.getRoom(newroom.getId()).get();
		newroom.setBookings(roomService.getFutureBookingsById(newroom.getId()));
		roomService.updateRoom(newroom.getId(),newroom);
	}
	
	public List<Booking> getByUserId(int id){
		return bookingDao.getByUserId(id);
	}
	
	public List<Booking> getLatestBookings(){
		return bookingDao.getLatestBookings();
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
