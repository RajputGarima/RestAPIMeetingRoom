package com.adobe.prj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adobe.prj.entity.Booking;

public interface BookingDao extends JpaRepository<Booking, Integer>{

}
