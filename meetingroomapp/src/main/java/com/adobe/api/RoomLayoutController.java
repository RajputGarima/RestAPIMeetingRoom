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

import com.adobe.entity.RoomLayout;
import com.adobe.service.RoomLayoutService;


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
		return service.getRoomLayout(id);
	}
	
	@PostMapping()
	public @ResponseBody RoomLayout adsRoomLayout(@RequestBody RoomLayout r) {
		  return service.addRoomLayout(r);
		}
}
