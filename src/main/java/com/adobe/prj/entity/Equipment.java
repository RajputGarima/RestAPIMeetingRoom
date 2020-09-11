package com.adobe.prj.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.adobe.prj.util.PriceType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Table
@Entity
public class Equipment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int equipId;
	
	private String name;
	private boolean multiUnits;
	private double price;
	private PriceType priceType;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "equipments")
	List<Booking> bookings = new ArrayList<Booking>();
		
	
	public List<Booking> getBookings() {
		return bookings;
	}
	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
	public boolean isMultiUnits() {
		return multiUnits;
	}
	public void setMultiUnits(boolean multiUnits) {
		this.multiUnits = multiUnits;
	}
	public int getEquipId() {
		return equipId;
	}
	public void setEquipId(int equipId) {
		this.equipId = equipId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Enumerated(EnumType.ORDINAL)
	public PriceType getPriceType() {
		return priceType;
	}
	public void setPriceType(PriceType priceType) {
		this.priceType = priceType;
	}

}
