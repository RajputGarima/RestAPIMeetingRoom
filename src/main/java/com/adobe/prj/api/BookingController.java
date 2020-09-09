package com.adobe.prj.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	private BookingService service;
	
//	 GET http://localhost:8080/api/customers
	@GetMapping()
    public @ResponseBody List<Booking> getBookings() {
        return service.getBookings();
    }
	
	@GetMapping("/{id}")
	public @ResponseBody Booking getBooking(@PathVariable("id") int id) {
		return service.getBooking(id);
	}
	
	@PostMapping()
	public @ResponseBody Booking addBooking(@RequestBody Booking b) {
	  return service.addBooking(b);
	}
	
}
