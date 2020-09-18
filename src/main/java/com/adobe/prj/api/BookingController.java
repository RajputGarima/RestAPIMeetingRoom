package com.adobe.prj.api;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

import com.adobe.prj.entity.Booking;
import com.adobe.prj.entity.Equipment;
import com.adobe.prj.entity.EquipmentDetail;
import com.adobe.prj.entity.Food;
import com.adobe.prj.entity.FoodBooking;
import com.adobe.prj.entity.Room;
import com.adobe.prj.entity.RoomLayout;
import com.adobe.prj.entity.User;
import com.adobe.prj.exception.ExceptionNotFound;
import com.adobe.prj.service.BookingService;
import com.adobe.prj.service.EquipmentService;
import com.adobe.prj.service.FoodService;
import com.adobe.prj.service.RoomLayoutService;
import com.adobe.prj.service.RoomService;
import com.adobe.prj.service.UserService;
import com.adobe.prj.util.BookingStatus;


@RestController
@RequestMapping("api/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private RoomLayoutService layoutService;
	
	@Autowired
	private EquipmentService equipmentService;
	
	@Autowired
	private FoodService foodService;
	
	
	// fetch all bookings
	@GetMapping()
    public @ResponseBody List<Booking> getBookings() {
        return bookingService.getBookings();
    }
	
	// booking with id = 'id'
	@GetMapping("/{id}")
	public @ResponseBody Booking getBooking(@PathVariable("id") int id) {
		try {
			verifyBookingId(id);
		}catch(Exception e) {
			throw new ExceptionNotFound(e.getMessage());
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
	
	// add new booking
	@PostMapping()
	public @ResponseBody Booking addBooking(@RequestBody Booking b) {	
		try {
			verifyBookingContent(b);
		}catch(Exception e) {
			throw new ExceptionNotFound(e.getMessage());
		}
	
		User user = userService.addUser(b.getUser());
		b.setUser(user);

		return bookingService.addBooking(b);
	}
	
	// delete booking with id = 'id'
	@DeleteMapping("/{id}")
	public @ResponseBody void deleteBooking(@PathVariable("id") int id) {
		try {
			verifyBookingId(id);
		}catch(Exception e) {
			throw new ExceptionNotFound(e.getMessage());
		}
		bookingService.deleteBooking(id);
	}
	
	// update booking
	@PutMapping()
	public @ResponseBody Booking updateBooking(@RequestBody Booking b) {
		try {
			verifyBookingId(b.getId());
			verifyBookingContent(b);
		}catch(Exception e) {
			throw new ExceptionNotFound(e.getMessage());
		}
		User user = userService.addUser(b.getUser());
		b.setUser(user);
//		b.setUser(b.getUser());
		return bookingService.addBooking(b);
	}
	
	// update status of booking to say, 'CONFIRMED'
	// http://localhost:8080/api/bookings/2/CONFIRMED
	@PutMapping("/{id}/{status}")
	public @ResponseBody Booking updateBookingStatus(@PathVariable("id") int id, @PathVariable("status") BookingStatus status) {				
		try {
			verifyBookingId(id);
		}catch(Exception e) {
			throw new ExceptionNotFound(e.getMessage());
		}
		
		Booking booking = bookingService.getBooking(id).get();
		booking.setStatus(status);
		bookingService.addBooking(booking);
		return booking;
	}
	
	// verify content before adding a new booking
	public void verifyBookingContent(Booking b) throws ExceptionNotFound {
		Room room = b.getRoom();
		Optional<Room> r = roomService.getRoom(room.getId());
		if(!r.isPresent())
			throw new ExceptionNotFound("Room doesn't exist");
		
		RoomLayout layout = b.getRoomLayout();
		Optional<RoomLayout> rl = layoutService.getRoomLayout(layout.getId());
		if(!rl.isPresent())
			throw new ExceptionNotFound("Layout doesn't exist");
		
		List<EquipmentDetail> equipDetails = b.getEquipDetails();
		for(EquipmentDetail ed: equipDetails) {
			Equipment equipment = ed.getEquipment();
			Optional<Equipment> e = equipmentService.getEquipment(equipment.getId());
			if(!e.isPresent())
				throw new ExceptionNotFound("Equipment doesn't exist");
		}
		
		List<FoodBooking> foodBookings = b.getFoods();
		for(FoodBooking fb: foodBookings) {
			Food food = fb.getFood();
			Optional<Food> f = foodService.getFood(food.getId());
			if(!f.isPresent())
				throw new ExceptionNotFound("Food doesn't exist");
		}
		
		// layout exists for that room
		// totalCost >= eqpcost + foodcost + roomcost
		// initial status of booking
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date today = new Date();
		Date todayWithZeroTime = null;
		try {
			todayWithZeroTime = formatter.parse(formatter.format(today));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(b.getSchedule().getBookedFor().compareTo(todayWithZeroTime) < 0)
			throw new ExceptionNotFound("Booking date cannot be an old date ");
		
		
		if(b.getAttendees() > r.get().getCapacity())
			throw new ExceptionNotFound("Attendees cannot exceed Room's capacity");
		
			
	}
	
	public void verifyBookingId(int id) {
		Optional<Booking> b = bookingService.getBooking(id);
		if(!b.isPresent())
			throw new ExceptionNotFound("Booking with id " + id + " doesn't exist");	
	}
	
}
