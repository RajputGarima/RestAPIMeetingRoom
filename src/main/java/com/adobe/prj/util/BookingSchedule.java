package com.adobe.prj.util;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class BookingSchedule {

	
	@Temporal(TemporalType.TIMESTAMP)
	private Date bookedOn = new Date();
	
	private Date bookedFor;
	
	public Date getBookedOn() {
		return bookedOn;
	}

	public void setBookedOn(Date bookedOn) {
		this.bookedOn = bookedOn;
	}
	
	public Date getBookedFor() {
		return bookedFor;
	}

	public void setBookedFor(Date bookedFor) {
		this.bookedFor = bookedFor;
	}
	
	private Time startTime;
	private Time endTime;
	
	public BookingSchedule() {
		super();
	}

	public BookingSchedule(Date date, Time startTime, Time endTime) {
		super();
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getStartTime() {
		return startTime;
	}
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	public Time getEndTime() {
		return endTime;
	}
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}	
	
}
