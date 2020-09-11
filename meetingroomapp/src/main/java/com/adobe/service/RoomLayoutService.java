package com.adobe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public RoomLayout addRoomLayout(RoomLayout r) {
		return roomLayoutDao.save(r);
	}
}
