package com.adobe.prj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adobe.prj.entity.Booking;


public interface BookingDao extends JpaRepository<Booking, Integer>{
	
	@Query(value = "select * from booking where user_fk= :pr", nativeQuery = true) 
	public List<Booking> getByUserId(@Param("pr") int id);
	
	@Query(value = "select * from booking where room_fk= :pr", nativeQuery = true)
	public List<Booking> getByRoomId(@Param("pr") int id);

}
