package com.adobe.prj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adobe.prj.entity.Booking;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BookingDao extends JpaRepository<Booking, Integer>{
	
	@Query(value = "select * from booking where user_fk= :pr", nativeQuery = true) 
	public List<Booking> getByEmail(@Param("pr") String email);
	
	
	@Query(value = "select * from booking where room_fk= :pr", nativeQuery = true)
	public List<Booking> getByRoomId(@Param("pr") int id);

}
