package com.adobe.prj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adobe.prj.entity.Room;

@Repository
public interface RoomDao extends JpaRepository<Room, Integer>{

}

