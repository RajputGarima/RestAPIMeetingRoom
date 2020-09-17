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

import com.adobe.prj.entity.Equipment;
import com.adobe.prj.exception.ExceptionNotFound;
import com.adobe.prj.service.EquipmentService;

@RestController
@RequestMapping("api/equipments")
public class EquipmentController {
	
	@Autowired
	private EquipmentService equipmentService;

	// fetch all equipments
	@GetMapping()
    public @ResponseBody List<Equipment> getFoods() {
        return equipmentService.getEquipments();
    }

	// equipment with id = 'id'
	@GetMapping("/{id}")
	public @ResponseBody Equipment getEquipment(@PathVariable("id") int id) {
		try {
			Optional<Equipment> e = equipmentService.getEquipment(id);
			if(!e.isPresent())
				throw new ExceptionNotFound("Equipment with id " + id + " doesn't exist");
		}catch(Exception e) {	
			e.printStackTrace();
		}
		return equipmentService.getEquipment(id).get();
	}
	
	// add a new equipment
	@PostMapping()
	public @ResponseBody Equipment addEquipment(@RequestBody Equipment e) {
		return equipmentService.addEquipment(e);
	}
	
	// delete an equipment
	@DeleteMapping("/{id}")
    public @ResponseBody void deletEquipment(@PathVariable int id) {
		try {
			Optional<Equipment> e = equipmentService.getEquipment(id);
			if(!e.isPresent())
				throw new ExceptionNotFound("Equipment with id " + id + " doesn't exist");
		}catch(Exception e) {	
			e.printStackTrace();
		}
		Equipment e = equipmentService.getEquipment(id).get();
        equipmentService.deleteEquipment(e);
    }
	
	// update an equipment
	@PutMapping()
	public @ResponseBody Equipment updateEquipment(@RequestBody Equipment e) {
		return equipmentService.addEquipment(e);
	}
	

}
