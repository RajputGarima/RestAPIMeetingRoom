package com.adobe.prj.entity;




import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;



import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;








@Table
@Entity
public class Equipment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	

	@Column(unique = true)
	@NotNull(message = "Equipment Name cannot be NULL")
	private String title;
	
	@Min(1)
	private double price;
	
	private boolean multiUnits;
	private boolean hourlyAllowed;
//	private PriceType priceType;

	
//	@JsonIgnore
//	@ManyToMany(mappedBy = "equipments")
//	List<Booking> bookings = new ArrayList<Booking>();
		
	
//	public List<Booking> getBookings() {
//		return bookings;
//	}
//	public void setBookings(List<Booking> bookings) {
//		this.bookings = bookings;
//	}
	public boolean isMultiUnits() {
		return multiUnits;
	}
	public void setMultiUnits(boolean multiUnits) {
		this.multiUnits = multiUnits;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;

	}
	public boolean isHourlyAllowed() {
		return hourlyAllowed;
	}
	public void setHourlyAllowed(boolean hourlyAllowed) {
		this.hourlyAllowed = hourlyAllowed;
	}

//	@Enumerated(EnumType.ORDINAL)
//	public PriceType getPriceType() {
//		return priceType;
//	}
//	public void setPriceType(PriceType priceType) {
//		this.priceType = priceType;
//	}


}
