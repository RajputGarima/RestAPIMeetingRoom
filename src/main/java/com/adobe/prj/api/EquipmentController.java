package com.adobe.prj.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.adobe.prj.service.EquipmentService;

@RestController
@RequestMapping("api/equipments")
public class EquipmentController {
	
	@Autowired
	private EquipmentService equipmentService;
	
	@GetMapping()
	public @ResponseBody List<Equipment> getEquipments() {
		return equipmentService.getEquipments();
	}
	
	@GetMapping("/{id}")
	public @ResponseBody Equipment getEquipment(@PathVariable("id") int id) {
		return equipmentService.getEquipment(id);
	}
	
	@PostMapping()
	public @ResponseBody Equipment addEquipment(@RequestBody Equipment e) {
		return equipmentService.addEquipment(e);
	}
	
	@DeleteMapping("/{id}")
    public Map<String, Boolean> deletePost(@PathVariable int id) {
		Equipment e = equipmentService.getEquipment(id);
//		List<Booking> bookingsList = e.getBookings();
//		
//		for(Booking b: bookingsList) {
//			b.getEquipments().remove(e);
//		}
        equipmentService.deleteEquipment(e);
        
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
    }
	
	@PutMapping()
	public @ResponseBody Equipment updateEquipment(@RequestBody Equipment e) {
//		Equipment e1 = equipmentService.getEquipment(e.getEquipId());
		return equipmentService.addEquipment(e);
	}
	

}
