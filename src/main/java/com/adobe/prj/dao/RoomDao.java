package com.adobe.prj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adobe.prj.entity.Room;

public interface RoomDao extends JpaRepository<Room, Integer>{
	
}

