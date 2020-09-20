package com.adobe.prj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adobe.prj.entity.Room;

public interface RoomDao extends JpaRepository<Room, Integer>{
	
	@Query(value="select * from room where status = 1", nativeQuery = true)
	List<Room> getRoomsForUser();
	
}

