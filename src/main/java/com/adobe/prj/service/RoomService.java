package com.adobe.prj.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.adobe.prj.dao.BookingDao;
import com.adobe.prj.dao.RoomDao;
import com.adobe.prj.dao.RoomLayoutDao;
import com.adobe.prj.entity.Booking;
import com.adobe.prj.entity.Room;
import com.adobe.prj.entity.RoomLayout;
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
	
	public Room getRoom(int id) {
		Room R  = roomDao.findById(id).get();
		R.getRoomLayouts();
		return R;
	}
	
	@Transactional
	public Room addRoom(Room r) {
		
		List<RoomLayout> roomLayouts =  r.getRoomLayouts();
		List<RoomLayout> newRoomLayouts = new ArrayList<>();
		for(RoomLayout l : roomLayouts)
		{
			RoomLayout rl = roomLayoutDao.findById(l.getLayoutId()).get();
			newRoomLayouts.add(rl);
		}
		if(newRoomLayouts.contains(roomLayoutDao.findByTitle(DEFAULT_LAYOUT))) {
			r.setRoomLayouts(newRoomLayouts);
		}else {
			newRoomLayouts.add(roomLayoutDao.findByTitle(DEFAULT_LAYOUT));
			r.setRoomLayouts(newRoomLayouts);
		}
		return roomDao.save(r);
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
			RoomLayout rl = roomLayoutDao.findById(l.getLayoutId()).get();
			newRoomLayouts.add(rl);
		}

		if(newRoomLayouts.contains(roomLayoutDao.findByTitle(DEFAULT_LAYOUT))) {
			oldr.setRoomLayouts(newRoomLayouts);
		}else {
//			A message in the response body is to be added stating that
//			default layout cannot be removed
			newRoomLayouts.add(roomLayoutDao.findByTitle(DEFAULT_LAYOUT));
			oldr.setRoomLayouts(newRoomLayouts);
		}
		return roomDao.save(oldr);
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
}
