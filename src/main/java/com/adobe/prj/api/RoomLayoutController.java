package com.adobe.prj.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.prj.entity.RoomLayout;
import com.adobe.prj.service.RoomLayoutService;
import com.adobe.prj.validation.ValidJson;
import static com.adobe.prj.validation.SchemaLocations.LAYOUTSCHEMA;


@RestController
@RequestMapping("api/roomlayouts")
public class RoomLayoutController {
	@Autowired
	private RoomLayoutService service;
	
	@GetMapping()
	public @ResponseBody List<RoomLayout> getRoomLayouts() {
		return service.getRoomLayouts();
	}
	
	@GetMapping("/{id}")
	public @ResponseBody RoomLayout getRoomLayout(@PathVariable("id") int id) {
		return service.getRoomLayout(id).get();
	}
	
	@PostMapping()
	public @ResponseBody RoomLayout adsRoomLayout(@ValidJson(LAYOUTSCHEMA) RoomLayout r) {
		  return service.addRoomLayout(r);
		}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteRoomLayout(@PathVariable("id") int id){
		return service.deleteRoomLayout(id);
	}
	
	@PutMapping("/{id}")
	public @ResponseBody RoomLayout updateRoomLayout(@PathVariable("id") int id, @ValidJson(LAYOUTSCHEMA) RoomLayout r) {
		return service.updateRoomLayout(id, r);
	}
}
