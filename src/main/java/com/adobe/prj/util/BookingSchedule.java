package com.adobe.prj.util;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Embeddable
public class BookingSchedule {

	
	@Temporal(TemporalType.DATE)
	private Date bookedOn = new Date();
	
	@Temporal(TemporalType.DATE)
	private Date bookedFor;
	
	@NotNull(message = "Time Slots cannot be NULL")
	private int timeSlots;
	
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
	
//	private Time startTime;
//	private Time endTime;
	
	public BookingSchedule() {
		super();
	}

	public BookingSchedule(Date bookedOn, Date bookedFor, int timeSlots) {
		super();
		this.bookedOn = bookedOn;
		this.bookedFor = bookedFor;
		this.timeSlots = timeSlots;
	}

	public int getTimeSlots() {
		return timeSlots;
	}

	public void setTimeSlots(int timeSlots) {
		this.timeSlots = timeSlots;
	}
	
}
