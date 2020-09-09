package com.adobe.prj.entity;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//import ch.qos.logback.core.util.Duration;

@Table(name="booking")
@Entity
public class Booking {
	
	@Id
	private int bookingId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="order_date")
	private Date orderDate = new Date();
	
	private int attendees;
	private String room;
	private String user;
	
//	private List<String> food = new ArrayList<String>();
//	
//	private List<String> equipments = new ArrayList<String>();
//	
	private double totalCost;
	
//	Instant start;
//	Instant end;
//	        
//	Duration duration = Duration.between(start, end);
	
	public Booking() {
//		super();
	}

//	public Booking(String start, String end) {
//		
//		this.start = Instant.parse(start);
//		this.end = Instant.parse(end);
//	}

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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

//	public List<String> getFood() {
//		return food;
//	}
//
//	public void setFood(List<String> food) {
//		this.food = food;
//	}
//
//	public List<String> getEquipments() {
//		return equipments;
//	}
//
//	public void setEquipments(List<String> equipments) {
//		this.equipments = equipments;
//	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

//	public Instant getStart() {
//		return start;
//	}
//
//	public void setStart(Instant start) {
//		this.start = start;
//	}
//
//	public Instant getEnd() {
//		return end;
//	}
//
//	public void setEnd(Instant end) {
//		this.end = end;
//	}
//
//	public Duration getDuration() {
//		return duration;
//	}
//
//	public void setDuration(Duration duration) {
//		this.duration = duration;
//	}


}
