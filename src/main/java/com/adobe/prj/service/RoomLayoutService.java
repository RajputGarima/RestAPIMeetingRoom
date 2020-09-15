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
public class RoomLayoutService {

	@Autowired
	private RoomLayoutDao roomLayoutDao;
	
	@Autowired
	private RoomDao roomDao;
	
	public List<RoomLayout> getRoomLayouts(){
		return roomLayoutDao.findAll();
	}
	
	public RoomLayout getRoomLayout(int id) {
		return roomLayoutDao.findById(id).get();
	}
	
	@Transactional
	public RoomLayout addRoomLayout(RoomLayout r) {
		List<Room> rooms =  r.getRooms();
		List<Room> newRooms = new ArrayList<>();
		for(Room i : rooms)
		{
			Room R = roomDao.findById(i.getRoomId()).get();
			newRooms.add(R);
		}
		r.setRooms(newRooms);
		return roomLayoutDao.save(r);
	}
	
	@Transactional
	public RoomLayout updateRoomLayout(int id,RoomLayout newr){
		RoomLayout oldr = roomLayoutDao.findById(id).get();
		oldr.setTitle(newr.getTitle());
		oldr.setImageUrl(newr.getImageUrl());
		List<Room> rooms = newr.getRooms();
		List<Room> newRooms = new ArrayList<>();
		for(Room r: rooms) {
			Room R = roomDao.findById(r.getRoomId()).get();
			newRooms.add(R);
		}
		oldr.setRooms(newRooms);
		return roomLayoutDao.save(oldr);
	}
	@Transactional
    public ResponseEntity<Object> deleteRoomLayout(int id) {
        if(roomLayoutDao.findById(id).isPresent()) {
            roomLayoutDao.deleteById(id);
            if(roomLayoutDao.findById(id).isPresent())
                return ResponseEntity.unprocessableEntity().body("Failed to Delete the record");
            else return ResponseEntity.ok().body("Successfully deleted the record");
        } else return ResponseEntity.badRequest().body("Cannot find the record");
    }
}
