package com.adobe.prj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
	
	@Min(0)
	private double price;
	
	private boolean multiUnits;
	private boolean hourlyAllowed;

	public Equipment() {
		super();
	}

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

}
