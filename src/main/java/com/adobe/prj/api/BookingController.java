package com.adobe.prj.api;

import java.time.LocalDate;
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

import com.adobe.prj.dao.BookingDao;
import com.adobe.prj.dao.RoomDao;
import com.adobe.prj.entity.Booking;
import com.adobe.prj.entity.Room;
import com.adobe.prj.entity.User;
import com.adobe.prj.exception.ExceptionNotFound;
import com.adobe.prj.service.BookingService;
import com.adobe.prj.service.UserService;
import com.adobe.prj.util.BookingStatus;


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
	
	@Autowired
	private BookingDao bookingDao;

	
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
		try {
			Optional<Booking> b = bookingService.getBooking(id);
			if(!b.isPresent())
				throw new ExceptionNotFound("Booking doesn't exist");
		}catch(Exception e) {	
			e.printStackTrace();
		}	
		return bookingService.getBooking(id).get();
	}
	
	// top 3 upcoming bookings - booked_for
	@GetMapping("/upcomingBookings")
	public @ResponseBody List<Booking> getUpcomingBookings(){
		return bookingService.getUpcomingBookings(LocalDate.now());
	}
	
	// top 2 upcoming bookings on this 'date' - booked_for
	@GetMapping("/upcomingBookings/{date}")
	public @ResponseBody List<Booking> getBookingByDate(@PathVariable("date") String date){
		return bookingService.getBookingByDate(date);
	}
	
	// total bookings made so far
	@GetMapping("/totalBookings")
	public ResponseEntity<Long> getBookingsCount() {
	    return new ResponseEntity<>(bookingService.getBookingsCount(), HttpStatus.OK);
	}
	
	// total bookings scheduled for today - booked_for
	@GetMapping("/totalBookingsByDate")
	public ResponseEntity<Long> getBookingsCountByDate() {
	    return new ResponseEntity<>(bookingService.getBookingsCountByDate(), HttpStatus.OK);
	}
	
	// total bookings made today - booked_on
	@GetMapping("/totalBookingsToday")
	ResponseEntity<Long> getBookingsCountMadeToday() {
	    return new ResponseEntity<>(bookingService.getBookingsCountMadeToday(), HttpStatus.OK);
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
	
	@PutMapping()
	public @ResponseBody Booking updateBooking(@RequestBody Booking b) {
		  return bookingService.addBooking(b);
	}
	
	// change status of booking to CONFIRMED
	// http://localhost:8080/api/bookings/2/CONFIRMED
	@PutMapping("/{id}/{status}")
	public @ResponseBody Booking updateBookingStatus(@PathVariable("id") int id, @PathVariable("status") BookingStatus status) {
		Booking b = bookingService.getBooking(id).get();
		b.setStatus(status);
		bookingService.addBooking(b);
		return b;
	}
	
}
