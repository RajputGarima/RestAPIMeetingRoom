package com.adobe.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.adobe.dao.RoomLayoutDao;
import com.adobe.entity.RoomLayout;

@Service
public class RoomLayoutService {

	@Autowired
	private RoomLayoutDao roomLayoutDao;
	
	public List<RoomLayout> getRoomLayouts(){
		return roomLayoutDao.findAll();
	}
	
	public RoomLayout getRoomLayout(int id) {
		return roomLayoutDao.findById(id).get();
	}
	
	@Transactional
	public RoomLayout addRoomLayout(RoomLayout r) {
		return roomLayoutDao.save(r);
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
