package com.adobe.prj.api;

import java.util.List;

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

import com.adobe.prj.entity.Booking;
import com.adobe.prj.service.BookingService;

@RestController
@RequestMapping("api/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
//	 GET http://localhost:8080/api/bookings
	@GetMapping()
    public @ResponseBody List<Booking> getBookings() {
        return bookingService.getBookings();
    }
	
	@GetMapping("/{id}")
	public @ResponseBody Booking getBooking(@PathVariable("id") int id) {
		return bookingService.getBooking(id);
	}
	
	@PostMapping()
	public @ResponseBody Booking addBooking(@RequestBody Booking b) {
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
