package com.adobe.prj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adobe.prj.entity.Room;

@Repository
public interface RoomDao extends JpaRepository<Room, Integer>{

}

