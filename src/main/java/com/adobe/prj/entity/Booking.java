package com.adobe.prj.entity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table(name="booking")
@Entity
public class Booking{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookingId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="order_date")
	private Date orderDate = new Date();
	
	private int attendees;
	private String room;
	private double totalCost;
	
	@ManyToMany(fetch = FetchType.LAZY)
	List<Equipment> equipments = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name="booking_fk")
	List<EquipmentDetail> equipDetails = new ArrayList<EquipmentDetail>();

	public List<Equipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
	}


	public List<EquipmentDetail> getEquipDetails() {
		return equipDetails;
	}

	public void setEquipDetails(List<EquipmentDetail> equipDetails) {
		this.equipDetails = equipDetails;
	}

	public Booking() {
		super();
	}

	@ManyToOne
	@JoinColumn(name="user_fk") 
	private User user;
	
	private Time start; 
	private Time end;
	
	
	
	@OneToMany(
	        cascade = CascadeType.ALL,
	        orphanRemoval = true, 
	        fetch= FetchType.EAGER)
	@JoinColumn(name="booking_fk")
	private List<FoodBooking> foods = new ArrayList<>();
		
	
	
	public Time getStart() {
		return start;
	}

	public void setStart(Time start) {

		this.start=start;
	}

	public Time getEnd() {
		return end;
	}

	public void setEnd(Time end) {
		this.end=end;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public int getAttendees() {
		return attendees;
	}

	public void setAttendees(int attendees) {
		this.attendees = attendees;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
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
	
}

