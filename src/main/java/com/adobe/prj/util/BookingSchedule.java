package com.adobe.prj.util;

import java.time.LocalDate;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;


@Embeddable
public class BookingSchedule {

	private LocalDate bookedOn = LocalDate.now();
	private LocalDate bookedFor;
	
	@NotNull(message = "Time Slots cannot be NULL")
	private String timeSlots;
	
	public BookingSchedule() {
		super();
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

	public String getTimeSlots() {
		return timeSlots;
	}

	public void setTimeSlots(String timeSlots) {
		this.timeSlots = timeSlots;
	}
	
}
