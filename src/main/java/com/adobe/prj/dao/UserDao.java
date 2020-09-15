package com.adobe.prj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adobe.prj.entity.User;

public interface UserDao extends JpaRepository<User,Integer>{

}
