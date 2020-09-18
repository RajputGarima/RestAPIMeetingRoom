package com.adobe.prj.api;

import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.prj.dao.RoomDao;
import com.adobe.prj.entity.Booking;
import com.adobe.prj.entity.Equipment;
import com.adobe.prj.entity.EquipmentDetail;
import com.adobe.prj.entity.Food;
import com.adobe.prj.entity.FoodBooking;
import com.adobe.prj.entity.Room;
import com.adobe.prj.entity.RoomLayout;
import com.adobe.prj.entity.User;
import com.adobe.prj.exception.ExceptionNotFound;
import com.adobe.prj.service.RoomLayoutService;
import com.adobe.prj.service.RoomService;
import com.adobe.prj.validation.ValidJson;
import static com.adobe.prj.validation.SchemaLocations.ROOMSCHEMA;;

@RestController
@RequestMapping("api/rooms")
public class RoomController {
	
	@Autowired
	private RoomService service;
	
	@Autowired
	private RoomLayoutService layoutService;
	
	@GetMapping()
	public @ResponseBody List<Room> getRooms() {
		return service.getRooms();
	}
	
	@GetMapping("/{id}")
	public @ResponseBody Room getRoom(@PathVariable("id") int id) {
		
		Optional<Room> room = service.getRoom(id);
		if (!room.isPresent())
			throw new ExceptionNotFound("Room with id " + id + " doesn't exist");
		return room.get();
	}
	
	@GetMapping("/{id}/{date}")
	public ResponseEntity<List<Integer>> getTimeSlotsById(@PathVariable("id") int id, @PathVariable("date") String date){
		return new ResponseEntity<>(service.getTimeSlotsById(id, date), HttpStatus.OK);
	}
	
	@GetMapping("/futureBookings/{id}")
	public ResponseEntity<Long> getFutureBookingsById(@PathVariable("id") int id){
		return new ResponseEntity<>(service.getFutureBookingsById(id), HttpStatus.OK);
	}
	
	@PostMapping()
	public @ResponseBody Room addRoom(@ValidJson(ROOMSCHEMA) Room r) {
		try {
			verifyRoomContent(r);
		}catch(Exception e) {
			throw new ExceptionNotFound(e.getMessage());
		}
		  return service.addRoom(r);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteRoom(@PathVariable("id") int id){
		Optional<Room> r = service.getRoom(id);
		if (!r.isPresent())
			throw new ExceptionNotFound("Room with id " + id + " doesn't exist");
		return service.deleteRoom(id);
	}
	
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
	
	public void verifyRoomContent(Room r) throws ExceptionNotFound {
		
		List<RoomLayout>layouts = r.getRoomLayouts();
		for(RoomLayout l : layouts) {
			Optional<RoomLayout> rl = layoutService.getRoomLayout(l.getId());
			if(!rl.isPresent())
				throw new ExceptionNotFound("Layout doesn't exist");
		}
	
	}
}

