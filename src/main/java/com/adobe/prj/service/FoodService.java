package com.adobe.prj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adobe.prj.dao.FoodDao;
import com.adobe.prj.entity.Food;

@Service
public class FoodService {
	
	@Autowired
	private FoodDao foodDao;
	
	public List<Food> getFoods(){
		return foodDao.findAll();
	}
	
	public Food getFood(int id) {
		return foodDao.findById(id).get(); 
	}
	
	public Food addFood(Food b) {
		return foodDao.save(b);
	}
	
	public void deleteFood(int id) {
		Food f = foodDao.findById(id).get(); 
		foodDao.delete(f);
	}

}
