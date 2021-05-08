package com.example.spring_boot_security_demo.exception;

import java.util.Date;

public class ExceptionResponse extends RuntimeException {

	private static final long serialVersionUID = 1553158672359955195L;

	private Date timestamp;
	private String message;
	private String details;

	public ExceptionResponse(Date timestamp,
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

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

	@Override
	public String toString() {
		return "ExceptionResponse [timestamp=" + timestamp + ", message=" + message + ", details=" + details + "]";
	}

}
