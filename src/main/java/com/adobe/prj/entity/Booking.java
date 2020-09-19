package com.adobe.prj.entity;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;


import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.adobe.prj.util.BookingSchedule;
import com.adobe.prj.util.BookingStatus;
import com.adobe.prj.util.BookingType;



@Table(name="booking")
@Entity
public class Booking{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	

	@Embedded
	private BookingSchedule schedule;
	
	@Min(1)
	private int attendees;
	private double totalCost;
	
	@NotNull(message = "Booking Status cannot be NULL")
	private BookingStatus status;
	private BookingType bookingType;
	
	@ManyToOne
	@JoinColumn(name="room_fk")
	private Room room;

	@ManyToOne
	@JoinColumn(name="user_fk") 
	private User user;
	
	@ManyToOne
	@JoinColumn(name="layout_fk")
	private RoomLayout roomLayout;
	

	@OneToMany(
	        cascade = CascadeType.ALL,
	        orphanRemoval = true, 
	        fetch= FetchType.EAGER)
	@JoinColumn(name="booking_fk")
	private List<FoodBooking> foods = new ArrayList<>();
	
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name="booking_fk")
	List<EquipmentDetail> equipDetails = new ArrayList<EquipmentDetail>();

	public Booking() {
		super();
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAttendees() {
		return attendees;
	}

	public void setAttendees(int attendees) {
		this.attendees = attendees;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List<FoodBooking> getFoods() {
		return foods;
	}

	public void setFoods(List<FoodBooking> foods) {
		this.foods = foods;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public RoomLayout getRoomLayout() {
		return roomLayout;
	}

	public void setRoomLayout(RoomLayout roomLayout) {
		this.roomLayout = roomLayout;
	}
	
	public List<EquipmentDetail> getEquipDetails() {
		return equipDetails;
	}

	public void setEquipDetails(List<EquipmentDetail> equipDetails) {
		this.equipDetails = equipDetails;
	}
	
	public BookingSchedule getSchedule() {
		return schedule;
	}
	
	public void setSchedule(BookingSchedule schedule) {
		this.schedule = schedule;

	}

	@Enumerated(EnumType.ORDINAL)
	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;

	}

	@Enumerated(EnumType.ORDINAL)
	public BookingType getBookingType() {
		return bookingType;
	}


	public void setBookingType(BookingType bookingType) {
		this.bookingType = bookingType;
	}	
	
}


