package com.adobe.prj.api;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.adobe.prj.exception.CustomException;
import com.adobe.prj.exception.ExceptionMessage;
import com.adobe.prj.exception.ExceptionNotFound;

import com.adobe.prj.exception.ExceptionTokenExpired;
import com.adobe.prj.exception.JsonValidationFailedException;


@ControllerAdvice
@RestController
public class ExceptionController extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(ExceptionNotFound.class)
	  public final ResponseEntity<ExceptionMessage> handleUserNotFoundException(ExceptionNotFound ex, WebRequest request) {
		ExceptionMessage exceptionMessage = new ExceptionMessage(LocalDate.now(), ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ExceptionTokenExpired.class)
	public final ResponseEntity<ExceptionMessage> handleTokenExpiredException(ExceptionTokenExpired ex, WebRequest request) {
		ExceptionMessage exceptionMessage = new ExceptionMessage(LocalDate.now(), ex.getMessage(),
		        request.getDescription(false));
		    return new ResponseEntity<>(exceptionMessage, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(JsonValidationFailedException.class)
	  public final ResponseEntity<ExceptionMessage> onJsonValidationFailedException(JsonValidationFailedException ex, WebRequest request) {
		ExceptionMessage exceptionMessage = new ExceptionMessage(LocalDate.now(), ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomException.class)
	  public final ResponseEntity<ExceptionMessage> handleUserNotFoundException(CustomException ex, WebRequest request) {
		ExceptionMessage exceptionMessage = new ExceptionMessage(LocalDate.now(), ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	 }
}
