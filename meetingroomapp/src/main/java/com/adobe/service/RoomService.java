package com.adobe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adobe.dao.RoomDao;
import com.adobe.dao.RoomLayoutDao;
import com.adobe.entity.Room;
import com.adobe.entity.RoomLayout;


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
}
