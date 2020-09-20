package com.adobe.prj.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;


import javax.persistence.ConstraintMode;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.adobe.prj.service.CustomLayoutSerializer;
import com.adobe.prj.util.RoomBookingType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.adobe.prj.service.CustomLayoutSerializer;


import javax.persistence.ConstraintMode;




@Table(name="room")
@Entity
public class Room {
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(name = "room_id")
	private int id;
	

	@Column(unique = true)
	@NotNull(message = "Room Name cannot be NULL")
	private String title;
	
	@Min(1)
	private double pricePerDay;
	
	@Min(1)
	private int capacity;
	
	@Min(0)
	private double pricePerHour;
	
	private int bookings;
	private String imageUrl;
	private boolean status;
	private String description;
	
	@Embedded
	private RoomBookingType bookingType;
	
	
	@ManyToMany(targetEntity = RoomLayout.class,cascade = {CascadeType.PERSIST, CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
	@JoinTable(
			name = "room_rlayout",
			joinColumns = { @JoinColumn(name = "room_id",nullable = false,updatable = false)},
			inverseJoinColumns = { @JoinColumn(name = "layout_id",nullable = false,updatable = false)},
					foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
			        inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
			)
//	@JsonManagedReference
//	@JsonBackReference
	@JsonSerialize(using = CustomLayoutSerializer.class)
	List<RoomLayout> roomLayouts = new ArrayList<>();
	

	public Room() {

	}

	

	public Room(@NotNull(message = "Room Name cannot be NULL") String title, @Min(1) double pricePerDay,
			@Min(1) int capacity, @Min(0) double pricePerHour, int bookings, String imageUrl, boolean status,
			String description, RoomBookingType bookingType, List<RoomLayout> roomLayouts) {
		this.title = title;
		this.pricePerDay = pricePerDay;
		this.capacity = capacity;
		this.pricePerHour = pricePerHour;
		this.bookings = bookings;
		this.imageUrl = imageUrl;
		this.status = status;
		this.description = description;
		this.bookingType = bookingType;
		this.roomLayouts = roomLayouts;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getBookings() {
		return bookings;
	}

	public void setBookings(int bookings) {
		this.bookings = bookings;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<RoomLayout> getRoomLayouts() {
		return roomLayouts;
	}

	public void setRoomLayouts(List<RoomLayout> roomLayouts) {
		this.roomLayouts = roomLayouts;
	}

	public double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RoomBookingType getBookingType() {
		return bookingType;
	}

	public void setBookingType(RoomBookingType bookingType) {
		this.bookingType = bookingType;
	}

}
