package com.adobe.prj.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.adobe.prj.validation.ValidJson;
import static com.adobe.prj.validation.SchemaLocations.LAYOUTSCHEMA;


@RestController
@RequestMapping("api/roomlayouts")
public class RoomLayoutController {
	
	@Autowired
	private RoomLayoutService service;
	
	@Autowired
	private RoomService roomService;
	
	// fetch all layouts
	@GetMapping()
	public @ResponseBody List<RoomLayout> getRoomLayouts() {
		return service.getRoomLayouts();
	}
	
	// layout with id = 'id'
	@GetMapping("/{id}")
	public @ResponseBody RoomLayout getRoomLayout(@PathVariable("id") int id) {
		return service.getRoomLayout(id).get();
	}
	
	// add a new layout
	@PostMapping()
	public @ResponseBody RoomLayout addRoomLayout(@ValidJson(LAYOUTSCHEMA) RoomLayout r) {  
		try {
			verifyRoomLayoutContent(r);
		}catch(Exception e) {
			throw new ExceptionNotFound(e.getMessage());
		}
		return service.addRoomLayout(r);
	}
	
	// delete a layout
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteRoomLayout(@PathVariable("id") int id){
		Optional<RoomLayout> rl = service.getRoomLayout(id);
		if (!rl.isPresent())
			throw new ExceptionNotFound("Layout with id " + id + " doesn't exist");
		return service.deleteRoomLayout(id);
	}
	
	// update a layout
	@PutMapping("/{id}")
	public @ResponseBody RoomLayout updateRoomLayout(@PathVariable("id") int id, @ValidJson(LAYOUTSCHEMA) RoomLayout r) {
		Optional<RoomLayout> rl = service.getRoomLayout(id);
		if (!rl.isPresent())
			throw new ExceptionNotFound("Layout with id " + id + " doesn't exist");
		try {
			verifyRoomLayoutContent(r);
		}catch(Exception e) {
			throw new ExceptionNotFound(e.getMessage());
		}
		return service.addLayout(r);
	}
	
	public void verifyRoomLayoutContent(RoomLayout rl) throws ExceptionNotFound {	
		List<Room>rooms = rl.getRooms();
		for(Room room : rooms) {
			Optional<Room> r = roomService.getRoom(room.getId());
			if(!r.isPresent())
				throw new ExceptionNotFound("Room doesn't exist ");
		}
	}
}
