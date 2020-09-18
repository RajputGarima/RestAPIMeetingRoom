package com.adobe.prj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adobe.prj.entity.RoomLayout;


public interface RoomLayoutDao extends JpaRepository<RoomLayout,Integer>{
	RoomLayout findByTitle(String defaultLayout);
}
