package com.adobe.prj.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


@Service
public class RoomService {

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
		r.setRoomLayouts(newRoomLayouts);
		return roomDao.save(r);
	}
	
	@Transactional
	public Room updateRoom(int id,Room newr){
		Room oldr = roomDao.findById(id).get();
//		if(newr.getTitle() != null)
			oldr.setTitle(newr.getTitle());
//		if(newr.getPricePerDay() != 0)
			oldr.setPricePerDay(newr.getPricePerDay());
//		if(newr.getCapacity() != 0)
			oldr.setCapacity(newr.getCapacity());
//		if(newr.getBookings() != 0)
			oldr.setBookings(newr.getBookings());
//		if(newr.getImageUrl() != null)
			oldr.setImageUrl(newr.getImageUrl());
//		if(newr.getRoomLayouts().size() != 0) {
			List<RoomLayout> roomLayouts =  newr.getRoomLayouts();
			List<RoomLayout> newRoomLayouts = new ArrayList<>();
			for(RoomLayout l : roomLayouts)
			{
				RoomLayout rl = roomLayoutDao.findById(l.getId()).get();
				newRoomLayouts.add(rl);
			}
			oldr.setRoomLayouts(newRoomLayouts);
//			newr.setRoomLayouts(newRoomLayouts);
//		}
		return roomDao.save(oldr);
//		return roomDao.save(newr);
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
}
