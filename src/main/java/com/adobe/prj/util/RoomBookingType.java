package com.adobe.prj.util;

import javax.persistence.Embeddable;

@Embeddable
public class RoomBookingType {
	
	private boolean hourly;
	private boolean halfDay;
	private boolean fullDay;
	
	public RoomBookingType() {
		this.hourly = true;
		this.halfDay = true;
		this.fullDay = true;
	}
	public RoomBookingType(boolean hourly, boolean halfDay, boolean fullDay) {
		super();
		this.hourly = hourly;
		this.halfDay = halfDay;
		this.fullDay = fullDay;
	}
	public boolean isHourly() {
		return hourly;
	}
	public void setHourly(boolean hourly) {
		this.hourly = hourly;
	}
	public boolean isHalfDay() {
		return halfDay;
	}
	public void setHalfDay(boolean halfDay) {
		this.halfDay = halfDay;
	}
	public boolean isFullDay() {
		return fullDay;
	}
	public void setFullDay(boolean fullDay) {
		this.fullDay = fullDay;
	}
	

}
