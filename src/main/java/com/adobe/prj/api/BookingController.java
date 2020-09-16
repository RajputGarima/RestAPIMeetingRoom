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

import com.adobe.prj.dao.RoomDao;
import com.adobe.prj.entity.Booking;
import com.adobe.prj.entity.Room;
import com.adobe.prj.entity.User;
import com.adobe.prj.exception.ExceptionNotFound;
import com.adobe.prj.service.BookingService;
import com.adobe.prj.service.UserService;


@RestController
@RequestMapping("api/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private UserService userService;

//	@Autowired
//	private EquipmentService equipmentService;
//	
//	@Autowired
//	private FoodService foodService;
//	
//	@Autowired
//	private RoomService roomService;
	
	@Autowired
	private RoomDao roomDao;

	
	@GetMapping()
    public @ResponseBody List<Booking> getBookings() {
		

//		long milli = 123456789999l;
//		Booking b = new Booking();
//		b.setAttendees(10);

//
//		b.setRoom(roomService.getRoom(1));
//		b.setTotalCost(2700);
//		java.sql.Time time = new java.sql.Time(milli);
//		BookingSchedule bs = new BookingSchedule();
//		bs.setStartTime(time);
//		bs.setEndTime(time);
//
//		b.setSchedule(bs);
////		b.setUser(userService.getUser("k@adobe.com"));

//		
//		EquipmentDetail ed = new EquipmentDetail();
//		ed.setEquipment(equipmentService.getEquipment(1));
//		ed.setUnits(10);
//		ed.setPrice(ed.getUnits() * equipmentService.getEquipment(1).getPrice());
//		
//		b.getEquipDetails().add(ed);
//		
//		EquipmentDetail ed1 = new EquipmentDetail();
//		ed1.setEquipment(equipmentService.getEquipment(2));
//		ed1.setUnits(20);
//		ed1.setPrice(ed1.getUnits() * equipmentService.getEquipment(2).getPrice());
//		
//		b.getEquipDetails().add(ed1);
//		
//		FoodBooking fb = new FoodBooking();
//		fb.setFood(foodService.getFood(1));
//		fb.setQuantity(6);
//		fb.setAmount(fb.getQuantity() * foodService.getFood(1).getFoodPrice());
//		
//		b.getFoods().add(fb);
//		
//		FoodBooking fb1 = new FoodBooking();
//		fb1.setFood(foodService.getFood(2));
//		fb1.setQuantity(10);
//		fb1.setAmount(fb1.getQuantity() * foodService.getFood(2).getFoodPrice());
//		
//		b.getFoods().add(fb1);
//		
//		FoodBooking fb2 = new FoodBooking();
//		fb2.setFood(foodService.getFood(3));
//		fb2.setQuantity(5);
//		fb2.setAmount(fb2.getQuantity() * foodService.getFood(3).getFoodPrice());
//		
//		b.getFoods().add(fb2);
//		
//		bookingService.addBooking(b);

        return bookingService.getBookings();
    }
	
	@GetMapping("/{id}")
	public @ResponseBody Booking getBooking(@PathVariable("id") int id) {
		return bookingService.getBooking(id);
	}
	
	@PostMapping()
	public @ResponseBody Booking addBooking(@RequestBody Booking b) {
		Room r = b.getRoom();
		
//		Room R = roomDao.findById(r.getRoomId()).get();
		Optional<Room> room = roomDao.findById(r.getRoomId());

		if (!room.isPresent())
			throw new ExceptionNotFound("Room doesn't exist");

		
		User user = userService.addUser(b.getUser());
		b.setUser(user);


		return bookingService.addBooking(b);


	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody void deleteBooking(@PathVariable("id") int id) {
		bookingService.deleteBooking(id);
	}
	
	@PutMapping("/{id}")
	public @ResponseBody Booking updateBooking(@RequestBody Booking b) {
		  return bookingService.addBooking(b);
	}
	
}
