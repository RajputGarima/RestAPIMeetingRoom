package com.adobe.prj.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLErrorCodes;
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
import com.adobe.prj.util.UpdateStatus;
import com.adobe.prj.validation.ValidJson;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Service
public class RoomService {
	
	public static final String DEFAULT_LAYOUT = "Classroom";
	
	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private BookingDao bookingDao;
	
	@Autowired
	private RoomLayoutDao roomLayoutDao;
	
	public List<Room> getRooms(){
		return roomDao.findAll();
	}
	
	public Optional<Room> getRoom(int id) {
		return roomDao.findById(id);
	}
	
	@Transactional
	public Room addRoom(Room r) {
		
		List<RoomLayout> roomLayouts =  r.getRoomLayouts();
		List<RoomLayout> newRoomLayouts = new ArrayList<>();
		for(RoomLayout l : roomLayouts)
		{
			RoomLayout rl = roomLayoutDao.findById(l.getId()).get();
			newRoomLayouts.add(rl);
		}

		if(newRoomLayouts.contains(roomLayoutDao.findByTitle(DEFAULT_LAYOUT))) {
			r.setRoomLayouts(newRoomLayouts);
		}else {
			newRoomLayouts.add(roomLayoutDao.findByTitle(DEFAULT_LAYOUT));
			r.setRoomLayouts(newRoomLayouts);
		}

		Room room = null;
		try {
			room = roomDao.save(r);
		}catch(DataIntegrityViolationException exp) {
			// unique constraint
		        throw new CustomException("integrity violation SQL " + exp.getMostSpecificCause());
		}
		catch(javax.validation.ConstraintViolationException exp) {
			// @Min, @NotNULL
			throw new CustomException("constraint violation - name -  " + exp.getConstraintViolations() );
		}
		return room;

	}
	
	@Transactional
	public Room updateRoom(int id,Room newr){
		Room oldr = roomDao.findById(id).get();
		
		oldr.setTitle(newr.getTitle());

		oldr.setPricePerDay(newr.getPricePerDay());

		oldr.setCapacity(newr.getCapacity());

		oldr.setBookings(newr.getBookings());

		oldr.setImageUrl(newr.getImageUrl());

		List<RoomLayout> roomLayouts =  newr.getRoomLayouts();
		List<RoomLayout> newRoomLayouts = new ArrayList<>();
		for(RoomLayout l : roomLayouts)
		{
			RoomLayout rl = roomLayoutDao.findById(l.getId()).get();
			newRoomLayouts.add(rl);
		}

		if(newRoomLayouts.contains(roomLayoutDao.findByTitle(DEFAULT_LAYOUT))) {

			oldr.setRoomLayouts(newRoomLayouts);

//			newr.setRoomLayouts(newRoomLayouts);
//		}
			
				

//		return roomDao.save(newr);

		}else {
//			A message in the response body is to be added stating that
//			default layout cannot be removed
			newRoomLayouts.add(roomLayoutDao.findByTitle(DEFAULT_LAYOUT));
			oldr.setRoomLayouts(newRoomLayouts);
		}
		Room room = null;
		try {
			room = roomDao.save(oldr);
		}catch(DataIntegrityViolationException exp) {
			// unique constraint
		        throw new CustomException("integrity violation SQL " + exp.getMostSpecificCause());
		}
		catch(javax.validation.ConstraintViolationException exp) {
			// @Min, @NotNULL
			throw new CustomException("constraint violation - name -  " + exp.getConstraintViolations() );
		}
		return room;

	}
	
	@Transactional
	public ResponseEntity<Object> deleteRoom(int id){
		if(roomDao.findById(id).isPresent()) {			
			// to delete all the bookings made for this room
			List<Booking> bookings = bookingDao.getByRoomId(id);
			
			for(Booking b: bookings) {
				bookingDao.delete(b);
			}	
			// 
			roomDao.deleteById(id);
            if (roomDao.findById(id).isPresent()) {
                return ResponseEntity.unprocessableEntity().body("Failed to delete the record");
            } else return ResponseEntity.ok().body("Successfully deleted the record");
		}else
			return ResponseEntity.unprocessableEntity().body("Cannot find the record");
		
	}

	public List<Integer> getTimeSlotsById(int id, String date) {
		return bookingDao.getTimeSlotsById(id, date);
	}

	public Long getFutureBookingsById(int id) {
		return bookingDao.getFutureBookingsCountByRoomId(id, LocalDate.now());
	}
	
	public UpdateStatus updateRoomStatus(Room r, String status) {
		UpdateStatus u = new UpdateStatus();
		if(status.matches("ACTIVE")) {
			try {
				r.setStatus(true);
				u.setStatus(true);
				roomDao.save(r);
			}catch(Exception exp) {
			        throw new CustomException("Couldn't update Status");
			}
		}else if(status.matches("INACTIVE")) {
			try {
				r.setStatus(false);
				u.setStatus(false);
				roomDao.save(r);
			}catch(Exception exp) {
			        throw new CustomException("Couldn't update Status");
			}
		}
		u.setId(r.getId());
		return u;
	}
	
}
