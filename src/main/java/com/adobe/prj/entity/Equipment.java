package com.adobe.prj.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.adobe.prj.util.PriceType;

@Table
@Entity
public class Equipment {
	
	@Id
	private int equipId;
	
	private String name;
	private boolean multiUnits;
	private double price;
	private PriceType priceType;
	
	
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
	public Boolean getMultiUnits() {
		return multiUnits;
	}
	public void setMultiUnits(Boolean multiUnits) {
		this.multiUnits = multiUnits;
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
