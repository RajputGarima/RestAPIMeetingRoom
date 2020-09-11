package com.adobe.prj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adobe.prj.entity.Food;

public interface FoodDao extends JpaRepository<Food,Integer> {

}
