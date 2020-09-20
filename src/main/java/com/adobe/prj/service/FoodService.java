package com.adobe.prj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.adobe.prj.dao.FoodDao;
import com.adobe.prj.entity.Food;
import com.adobe.prj.exception.CustomException;


@Service
public class FoodService {
	
	@Autowired
	private FoodDao foodDao;
	
	public List<Food> getFoods(){
		return foodDao.findAll();
	}
	
	public Optional<Food> getFood(int id) {
		return foodDao.findById(id); 
	}
	
	public Food addFood(Food b) {
		Food food = null;
		try {
			food = foodDao.save(b);
		}catch(DataIntegrityViolationException exp) {
			// unique constraint
		        throw new CustomException("integrity violation SQL " + exp.getMostSpecificCause());
		}
		catch(javax.validation.ConstraintViolationException exp) {
			// @Min, @NotNULL
			throw new CustomException("constraint violation - name -  " + exp.getConstraintViolations() );
		}
		return food;
	}
	
	public void deleteFood(int id) {
		Food f = foodDao.findById(id).get(); 
		foodDao.delete(f);
	}

}
