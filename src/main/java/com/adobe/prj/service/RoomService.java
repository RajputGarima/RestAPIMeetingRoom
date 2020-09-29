package com.adobe.prj.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.adobe.prj.dao.BookingDao;
import com.adobe.prj.dao.RoomDao;
import com.adobe.prj.dao.RoomLayoutDao;
import com.adobe.prj.entity.Booking;
import com.adobe.prj.entity.Room;
import com.adobe.prj.entity.RoomLayout;
import com.adobe.prj.exception.CustomException;
import com.adobe.prj.util.BookingStatus;


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
			String s = exp.getMostSpecificCause().getLocalizedMessage();
			int index= s.indexOf("key");
			s=s.substring(0,index-4);
			throw new CustomException(s + "not allowed.");
		}
		catch(javax.validation.ConstraintViolationException exp) {
			// @Min, @NotNULL
			if(r.getCapacity()<1)
				throw new CustomException("Capacity must be greater than or equal to 1");
			throw new CustomException("constraint violation - name -  " + exp.getConstraintViolations() );
		}
		return room;

	}
	
	@Transactional
	public Room updateRoom(int id,Room newr){
		Room oldr = roomDao.findById(id).get();
		
		oldr.setTitle(newr.getTitle());

		oldr.setPricePerDay(newr.getPricePerDay());
		
		oldr.setPricePerHour(newr.getPricePerHour());
		
		oldr.setDescription(newr.getDescription());
		
		oldr.setBookingType(newr.getBookingType());

		oldr.setCapacity(newr.getCapacity());

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

		}else {
			//	A message in the response body is to be added stating that
			//	default layout cannot be removed
			newRoomLayouts.add(roomLayoutDao.findByTitle(DEFAULT_LAYOUT));
			oldr.setRoomLayouts(newRoomLayouts);
		}
		
		if(oldr.getCapacity()<1)
			throw new CustomException("Capacity must be greater than or equal to 1");
		
	
//		if(roomDao.findByTitle(oldr.getTitle()).size()==1) {
//			System.out.println("******");
//			throw new CustomException("Duplicate entry "+ "'"+ oldr.getTitle()+ "' not allowed.");
//		}
		
		Room room = null;
		try {
			System.out.println(oldr.toString());
			room = roomDao.save(oldr);
		}catch(DataIntegrityViolationException exp) {
			// unique constraint
			String s = exp.getMostSpecificCause().getLocalizedMessage();
			int index= s.indexOf("key");
			s=s.substring(0,index-4);
			throw new CustomException(s + "not allowed.");
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
			// cancel all the bookings made for this room, set room_fk, layout_fk = null
			List<Booking> bookings = bookingDao.getByRoomId(id);
			
			for(Booking b: bookings) {
				b.setRoom(null);
				b.setRoomLayout(null);
				b.setStatus(BookingStatus.CANCELLED);
			}	
			roomDao.deleteById(id);
            if (roomDao.findById(id).isPresent()) {
                return ResponseEntity.unprocessableEntity().body("Failed to delete the record");
            } 
            else 
            	return ResponseEntity.ok().body("Successfully deleted the record");
		}
		else
			return ResponseEntity.unprocessableEntity().body("Cannot find the record");	
	}

	public List<String> getTimeSlotsById(int id, String date) {
		return bookingDao.getTimeSlotsById(id, date);
	}

	public Long getFutureBookingsById(int id) {
		return bookingDao.getFutureBookingsCountByRoomId(id, LocalDate.now());
	}
	
	public ResponseEntity<?> updateRoomStatus(Room r, boolean status) {
		System.out.println("STATUS "+status);
		if(status) {
			try {
				System.out.println("ROOM ID "+ r.getId());
				r.setStatus(true);
				roomDao.save(r);
			}catch(Exception exp) {
			        throw new CustomException("Couldn't update Status");
			}
		}else {
			try {
				r.setStatus(false);
				roomDao.save(r);
			}catch(Exception exp) {
			        throw new CustomException("Couldn't update Status");
			}
		}
		return ResponseEntity.ok("Status updated");
	}

	public List<Room> getRoomsForUser() {
		return roomDao.getRoomsForUser();
	}
	
}
