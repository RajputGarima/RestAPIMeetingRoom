package com.adobe.prj.exception;

import java.util.Date;

public class ExceptionMessage {
	
	private Date timestamp;
	private String message;
	private String path;
	  
	public ExceptionMessage(Date timestamp, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.path = path;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
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
