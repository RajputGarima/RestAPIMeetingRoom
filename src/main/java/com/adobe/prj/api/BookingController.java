package com.adobe.prj.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
=======
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
>>>>>>> 3d686a6d409962f44b8758a42da2d344de5941d5
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.prj.entity.Booking;
<<<<<<< HEAD
import com.adobe.prj.entity.EquipmentDetail;
import com.adobe.prj.service.BookingService;
import com.adobe.prj.service.EquipmentService;
=======
import com.adobe.prj.service.BookingService;
>>>>>>> 3d686a6d409962f44b8758a42da2d344de5941d5

@RestController
@RequestMapping("api/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
<<<<<<< HEAD
	@Autowired
	private EquipmentService equipmentService;
	
	@GetMapping()
    public @ResponseBody List<Booking> getBookings() {
		
//		Booking b = new Booking();
//		b.setAttendees(10);
//		b.setRoom("Large Conference Room");
//		b.setTotalCost(2700);
//		b.setUser("Garima");
//		b.getEquipments().add(equipmentService.getEquipment(1));
//		b.getEquipments().add(equipmentService.getEquipment(2));
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
//		bookingService.addBooking(b);
		
=======
//	 GET http://localhost:8080/api/bookings
	@GetMapping()
    public @ResponseBody List<Booking> getBookings() {
>>>>>>> 3d686a6d409962f44b8758a42da2d344de5941d5
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
	
<<<<<<< HEAD
=======
	@DeleteMapping("/{id}")
	public @ResponseBody void deleteBooking(@PathVariable("id") int id) {
		bookingService.deleteBooking(id);
	}
	
	@PutMapping("/{id}")
	public @ResponseBody Booking updateBooking(@RequestBody Booking b) {
		  return bookingService.addBooking(b);
		}
	
>>>>>>> 3d686a6d409962f44b8758a42da2d344de5941d5
}
