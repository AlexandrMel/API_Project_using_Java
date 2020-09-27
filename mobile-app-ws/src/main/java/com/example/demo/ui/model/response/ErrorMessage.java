package com.example.demo.ui.model.response;

import java.util.Date;
//Model for the Exception wrapper
public class ErrorMessage {
	
	public ErrorMessage() {}
	public ErrorMessage(Date timestamp, String message) {
		super();
		this.timestamp = timestamp;
		this.message = message;
	}

	private Date timestamp;
	private String message;

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
}
