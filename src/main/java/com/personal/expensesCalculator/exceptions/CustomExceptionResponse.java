package com.personal.expensesCalculator.exceptions;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class CustomExceptionResponse {
	private String message;
	private LocalDateTime timeStamp;
	private String details;
	public CustomExceptionResponse(String message, LocalDateTime timeStamp, String details) {
		super();
		this.message = message;
		this.timeStamp = timeStamp;
		this.details = details;
	}
}
