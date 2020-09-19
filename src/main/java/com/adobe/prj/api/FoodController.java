package com.adobe.prj.api;

import java.util.List;
import java.util.Optional;

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
import com.adobe.prj.exception.ExceptionNotFound;
import com.adobe.prj.service.FoodService;
import com.adobe.prj.validation.ValidJson;

import static com.adobe.prj.validation.SchemaLocations.FOODSCHEMA;

@RestController
@RequestMapping("api/foods")
public class FoodController {
	
	@Autowired
	private FoodService foodservice;
	
	// fetch all food
	@GetMapping()
    public @ResponseBody List<Food> getFoods() {
        return foodservice.getFoods();
    }
	
	// fetch food with id = 'id'
	@GetMapping("/{id}")
	public @ResponseBody Food getBooking(@PathVariable("id") int id) {
		try {
			verifyFoodId(id);
		}catch(Exception e) {
			throw new ExceptionNotFound(e.getMessage());
		}
		return foodservice.getFood(id).get();
	}
	
	// add new food
	@PostMapping()
	public @ResponseBody Food addFood(@ValidJson(FOODSCHEMA) Food f) {
	  return foodservice.addFood(f);
	}

	// delete a food
	@DeleteMapping("/{id}")
	public @ResponseBody void deleteFood(@PathVariable("id") int id) {
		try {
			verifyFoodId(id);
		}catch(Exception e) {
			throw new ExceptionNotFound(e.getMessage());
		}
		foodservice.deleteFood(id);
	}
	
	// update a food
	@PutMapping("/{id}")
	public @ResponseBody Food updateFood(@ValidJson(FOODSCHEMA) Food food) {
		Optional<Food> f = foodservice.getFood(food.getId());
		if(!f.isPresent())
			throw new ExceptionNotFound("Food with id " + food.getId() + " doesn't exist");
		  return foodservice.addFood(food);

	}
	
	public void verifyFoodId(int id) {
		Optional<Food> f = foodservice.getFood(id);
		if(!f.isPresent())
			throw new ExceptionNotFound("Food with id " + id + " doesn't exist");
	}

}
