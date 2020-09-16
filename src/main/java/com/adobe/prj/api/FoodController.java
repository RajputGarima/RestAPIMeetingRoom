package com.adobe.prj.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.prj.entity.Food;
import com.adobe.prj.service.FoodService;

@RestController
@RequestMapping("api/foods")
public class FoodController {
	
	@Autowired
	private FoodService foodservice;
	
	@GetMapping()
    public @ResponseBody List<Food> getFoods() {
        return foodservice.getFoods();
    }
	
	@GetMapping("/{id}")
	public @ResponseBody Food getBooking(@PathVariable("id") int id) {
		return foodservice.getFood(id);
	}
	
	@PostMapping()
	public @ResponseBody Food addFood(@RequestBody Food f) {
	  return foodservice.addFood(f);
	}

	@DeleteMapping("/{id}")
	public @ResponseBody void deleteFood(@PathVariable("id") int id) {
		foodservice.deleteFood(id);
	}
	
	@PutMapping("/{id}")
	public @ResponseBody Food updateFood(@RequestBody Food f) {
		  return foodservice.addFood(f);
	}
	

}
