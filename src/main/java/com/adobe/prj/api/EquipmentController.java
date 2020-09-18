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
import com.adobe.prj.validation.ValidJson;

import static com.adobe.prj.validation.SchemaLocations.EQUIPMENTSCHEMA;

@RestController
@RequestMapping("api/equipments")
public class EquipmentController {
	
	@Autowired
	private EquipmentService equipmentService;
	

//	@GetMapping()
//	public ResponseEntity<Equipment> getEquipments() {
//		HttpHeaders responseHeaders = new HttpHeaders();
//	    responseHeaders.set("Allow-Access-Control-Origin", 
//	      "*");
//	    return ResponseEntity.ok().headers(responseHeaders)
//	    	      .body(equipmentService.getEquipment(2));
//	}
	
	@GetMapping()
    public @ResponseBody List<Equipment> getFoods() {
        return equipmentService.getEquipments();
    }

	@GetMapping("/{id}")
	public @ResponseBody Equipment getEquipment(@PathVariable("id") int id) {
		return equipmentService.getEquipment(id);
	}
	
	@PostMapping()
	public @ResponseBody Equipment addEquipment(@ValidJson(EQUIPMENTSCHEMA) Equipment e) {
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
	public @ResponseBody Equipment updateEquipment(@ValidJson(EQUIPMENTSCHEMA) Equipment e) {
//		Equipment e1 = equipmentService.getEquipment(e.getEquipId());
		return equipmentService.addEquipment(e);
	}
	

}
