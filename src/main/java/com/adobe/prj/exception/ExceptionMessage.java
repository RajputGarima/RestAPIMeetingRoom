package com.adobe.prj.exception;

import java.time.LocalDate;

public class ExceptionMessage {
	
	private LocalDate timestamp;
	private String message;
	private String path;
	  
	public ExceptionMessage(LocalDate timestamp, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.path = path;
	}

	public LocalDate getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDate timestamp) {
		this.timestamp = timestamp;
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	  
}
