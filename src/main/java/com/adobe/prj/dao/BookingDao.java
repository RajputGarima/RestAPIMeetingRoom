package com.adobe.prj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adobe.prj.entity.Booking;


public interface BookingDao extends JpaRepository<Booking, Integer>{
	
	@Query(value = "select * from booking where user_fk= :pr", nativeQuery = true) 
	public List<Booking> getByEmail(@Param("pr") String email);

}

