package com.example.spring_boot_security_demo.student.exception;

import java.util.Date;

public class StudentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7402972540222001579L;
	
	private Date timestamp;
	private String message;
	private String details;

	public StudentNotFoundException() {
		super();
	}

	public StudentNotFoundException(Date timestamp,
			String message,
			String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "StudentNotFoundException [timestamp=" + timestamp + ", message=" + message + ", details=" + details
				+ "]";
	}
	
}
