package com.adobe.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.entity.Room;
import com.adobe.service.RoomService;

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
}

