package com.adobe.prj.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.prj.entity.Room;
import com.adobe.prj.entity.RoomLayout;
import com.adobe.prj.exception.ExceptionNotFound;
import com.adobe.prj.service.RoomLayoutService;
import com.adobe.prj.service.RoomService;
import com.adobe.prj.util.UpdateStatus;
import com.adobe.prj.validation.ValidJson;
import static com.adobe.prj.validation.SchemaLocations.ROOMSCHEMA;;

@RestController
@RequestMapping("api/rooms")
public class RoomController {
	
	@Autowired
	private RoomService service;
	
	@Autowired
	private RoomLayoutService layoutService;
	
	// fetch all rooms
	@GetMapping()
	public @ResponseBody List<Room> getRooms() {
		return service.getRooms();
	}
	
	// room with id = 'id'
	@GetMapping("/{id}")
	public @ResponseBody Room getRoom(@PathVariable("id") int id) {	
		Optional<Room> room = service.getRoom(id);
		if (!room.isPresent())
			throw new ExceptionNotFound("Room with id " + id + " doesn't exist");
		return room.get();
	}
	
	//booking timeSlots for the the room id on the given date(booked_for)
	@GetMapping("/{id}/{date}")
	public ResponseEntity<List<Integer>> getTimeSlotsById(@PathVariable("id") int id, @PathVariable("date") String date){
		return new ResponseEntity<>(service.getTimeSlotsById(id, date), HttpStatus.OK);
	}
	
	//number of confirmed future bookings for room id = 'id'
	@GetMapping("/futureBookings/{id}")
	public ResponseEntity<Long> getFutureBookingsById(@PathVariable("id") int id){
		return new ResponseEntity<>(service.getFutureBookingsById(id), HttpStatus.OK);
	}
	
	// add a room
	@PostMapping()
	public @ResponseBody Room addRoom(@ValidJson(ROOMSCHEMA) Room r) {
		try {
			verifyRoomContent(r);
		}catch(Exception e) {
			throw new ExceptionNotFound(e.getMessage());
		}
		  return service.addRoom(r);
	}
	
	// delete a room
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteRoom(@PathVariable("id") int id){
		Optional<Room> r = service.getRoom(id);
		if (!r.isPresent())
			throw new ExceptionNotFound("Room with id " + id + " doesn't exist");
		return service.deleteRoom(id);
	}
	
	// update a room
	@PutMapping("/{id}")
	public @ResponseBody Room updateRoom(@PathVariable("id") int id, @ValidJson(ROOMSCHEMA) Room r) {
		Optional<Room> room = service.getRoom(id);
		if (!room.isPresent())
			throw new ExceptionNotFound("Room with id " + id + " doesn't exist");
		try {
			verifyRoomContent(r);
		}catch(Exception e) {
			throw new ExceptionNotFound(e.getMessage());
		}
		return service.updateRoom(id, r);
	}
	
	// update status of a room
	@PutMapping("/{id}/{status}")
	public @ResponseBody UpdateStatus updateRoomStatus(@PathVariable("id") int id, @PathVariable("status") String status) {				
		Optional<Room> room = service.getRoom(id);
		if (!room.isPresent())
			throw new ExceptionNotFound("Room with id " + id + " doesn't exist");
		return service.updateRoomStatus(room.get(),status);
	}
	
	public void verifyRoomContent(Room r) throws ExceptionNotFound {	
		List<RoomLayout>layouts = r.getRoomLayouts();
		for(RoomLayout l : layouts) {
			Optional<RoomLayout> rl = layoutService.getRoomLayout(l.getId());
			if(!rl.isPresent())
				throw new ExceptionNotFound("Layout doesn't exist");
		}	
	}
}

