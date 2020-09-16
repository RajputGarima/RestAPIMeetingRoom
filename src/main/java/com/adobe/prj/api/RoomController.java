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

import com.adobe.prj.entity.Room;
import com.adobe.prj.service.RoomService;

@RestController
@RequestMapping("api/rooms")
public class RoomController {
	
	@Autowired
	private RoomService service;
	
	@GetMapping()
	public @ResponseBody List<Room> getRooms() {
		return service.getRooms();
	}
	
	@GetMapping("/{id}")
	public @ResponseBody Room getRoom(@PathVariable("id") int id) {
		return service.getRoom(id);
	}
	
	@PostMapping()
	public @ResponseBody Room addRoom(@RequestBody Room r) {
		  return service.addRoom(r);
		}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteRoom(@PathVariable("id") int id){
		return service.deleteRoom(id);
	}
	
	@PutMapping("/{id}")
	public @ResponseBody Room updateRoom(@PathVariable("id") int id, @RequestBody Room r) {
		return service.updateRoom(id, r);
	}
}

