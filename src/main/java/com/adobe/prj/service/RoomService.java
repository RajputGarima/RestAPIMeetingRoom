package com.adobe.prj.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.adobe.prj.dao.RoomDao;
import com.adobe.prj.dao.RoomLayoutDao;
import com.adobe.prj.entity.Room;
import com.adobe.prj.entity.RoomLayout;


@Service
public class RoomService {

	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private RoomLayoutDao roomLayoutDao;
	
	public List<Room> getRooms(){
		return roomDao.findAll();
	}
	
	public Room getRoom(int id) {
		return roomDao.findById(id).get();
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
		r.setRoomLayouts(newRoomLayouts);
		return roomDao.save(r);
	}
	
//	@Transactional
//	public Room updateRoom(int id,Room newr){
//		Room oldr = roomDao.findById(id).get();
//		return roomDao.save(newr);
//	}
	
	@Transactional
	public ResponseEntity<Object> deleteRoom(int id){
		if(roomDao.findById(id).isPresent()) {
			roomDao.deleteById(id);
            if (roomDao.findById(id).isPresent()) {
                return ResponseEntity.unprocessableEntity().body("Failed to delete the record");
            } else return ResponseEntity.ok().body("Successfully deleted the record");
		}else
			return ResponseEntity.unprocessableEntity().body("Cannot find the record");
		
	}
}
