package com.adobe.prj.util;

import java.time.LocalDate;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;


@Embeddable
public class BookingSchedule {

	private LocalDate bookedOn = LocalDate.now();
	private LocalDate bookedFor;
	
	@NotNull(message = "Time Slots cannot be NULL")
	private int timeSlots;
	
	public BookingSchedule() {
		super();
	}

	public int getTimeSlots() {
		return timeSlots;
	}

	public LocalDate getBookedOn() {
		return bookedOn;
	}

	public void setBookedOn(LocalDate bookedOn) {
		this.bookedOn = bookedOn;
	}

	public LocalDate getBookedFor() {
		return bookedFor;
	}

	public void setBookedFor(LocalDate bookedFor) {
		this.bookedFor = bookedFor;
	}

	public void setTimeSlots(int timeSlots) {
		this.timeSlots = timeSlots;
	}
	
}
