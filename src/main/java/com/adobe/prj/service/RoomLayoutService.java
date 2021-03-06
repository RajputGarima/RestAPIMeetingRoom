package com.adobe.prj.service;

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

@Service
public class RoomLayoutService {
	
	public static final String DEFAULT_LAYOUT = "Classroom";

	@Autowired
	private RoomLayoutDao roomLayoutDao;
	
	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private BookingDao bookingDao;
	
	public List<RoomLayout> getRoomLayouts(){
		return roomLayoutDao.findAll();
	}
	
	public Optional<RoomLayout> getRoomLayout(int id) {
		return roomLayoutDao.findById(id);
	}
	
	@Transactional
	public RoomLayout addRoomLayout(RoomLayout r) {
		List<Room> rooms =  r.getRooms();
		List<Room> newRooms = new ArrayList<>();
		for(Room i : rooms)
		{
			Room R = roomDao.findById(i.getId()).get();
			newRooms.add(R);
		}
		r.setRooms(newRooms);
		
		RoomLayout roomLayout = null;
		try {
			roomLayout = roomLayoutDao.save(r);
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
		return roomLayout;
	}
	
	@Transactional
	public RoomLayout updateRoomLayout(int id,RoomLayout newr){
		RoomLayout oldr = roomLayoutDao.findById(id).get();
		oldr.setTitle(newr.getTitle());
		oldr.setImageUrl(newr.getImageUrl());
		List<Room> rooms = newr.getRooms();
		List<Room> newRooms = new ArrayList<>();
		for(Room r: rooms) {
			Room R = roomDao.findById(r.getId()).get();
			newRooms.add(R);
		}
		oldr.setRooms(newRooms);
		
		RoomLayout roomLayout = null;
		try {
			roomLayout = roomLayoutDao.save(oldr);
		}catch(DataIntegrityViolationException exp) {
			// unique constraint
		        throw new CustomException("integrity violation SQL " + exp.getMostSpecificCause());
		}
		catch(javax.validation.ConstraintViolationException exp) {
			// @Min, @NotNULL
			throw new CustomException("constraint violation - name -  " + exp.getConstraintViolations() );
		}
		return roomLayout;
	}
	
	public RoomLayout addLayout(RoomLayout rl) {
		RoomLayout layout = null;
		try {
			layout = roomLayoutDao.save(rl);
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
		return layout;
	}
	
	@Transactional
    public ResponseEntity<Object> deleteRoomLayout(int id) {
		RoomLayout rl = roomLayoutDao.findById(id).get();
		if(rl.equals(roomLayoutDao.findByTitle(DEFAULT_LAYOUT))) {
			return ResponseEntity.unprocessableEntity().body("Default layout cannot be deleted");
		}
        if(roomLayoutDao.findById(id).isPresent()) {
        	
    		List<Booking> bookings = new ArrayList<>();
    		bookings = bookingDao.getByLayoutId(id);
    		RoomLayout defaultLayout = roomLayoutDao.findByTitle(DEFAULT_LAYOUT);
    		for(Booking b:bookings) {
    			b.setRoomLayout(defaultLayout);
    				bookingDao.save(b);
    		}
            roomLayoutDao.deleteById(id);
            if(roomLayoutDao.findById(id).isPresent())
                return ResponseEntity.unprocessableEntity().body("Failed to Delete the record");
            else
            	return ResponseEntity.ok().body("Successfully deleted the record");
        }
        else 
        	return ResponseEntity.badRequest().body("Cannot find the record");
    }
	
}
