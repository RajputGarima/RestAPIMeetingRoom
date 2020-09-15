package com.adobe.prj.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.ConstraintMode;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Table(name="room")
@Entity
public class Room {
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(name = "room_id")
	private int roomId;
	
	private String title;
	
	private String imageUrl;
	
	private int pricePerDay;
	
	private int capacity;
	
	private int bookings;
	
	private boolean status;
	
	@ManyToMany(targetEntity = RoomLayout.class,cascade = {CascadeType.PERSIST, CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
	@JoinTable(
			name = "room_rlayout",
			joinColumns = { @JoinColumn(name = "room_id",nullable = false,updatable = false)},
			inverseJoinColumns = { @JoinColumn(name = "layout_id",nullable = false,updatable = false)},
					foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
			        inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
			)
	List<RoomLayout> roomLayouts = new ArrayList<>();
	

	public Room() {

	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
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

	public int getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(int pricePerDay) {
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

}
