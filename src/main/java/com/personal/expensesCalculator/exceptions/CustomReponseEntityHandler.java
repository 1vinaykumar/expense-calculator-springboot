package com.personal.expensesCalculator.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@ControllerAdvice
public class CustomReponseEntityHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest wr) throws Exception {
		CustomExceptionResponse resp = new CustomExceptionResponse(ex.getMessage(),LocalDateTime.now(), wr.getDescription(false));
		return new ResponseEntity<Object>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest wr) throws Exception {
		CustomExceptionResponse resp = new CustomExceptionResponse(ex.getMessage(),LocalDateTime.now(), wr.getDescription(false));
		return new ResponseEntity<Object>(resp, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(UnauthorizedException.class)
	public final ResponseEntity<Object> handleUnauthorizedException(Exception ex, WebRequest wr) throws Exception {
		CustomExceptionResponse resp = new CustomExceptionResponse(ex.getMessage(),LocalDateTime.now(), wr.getDescription(false));
		return new ResponseEntity<Object>(resp, HttpStatus.UNAUTHORIZED);
	}
	

}
