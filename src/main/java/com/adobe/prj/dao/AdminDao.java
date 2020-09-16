package com.adobe.prj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adobe.prj.entity.Admin;

public interface AdminDao extends JpaRepository<Admin,Integer> {
	
	@Query(value = "select * from admin where email= :pr", nativeQuery = true)
	public Admin findByEmail(@Param("pr") String email);
	
}
