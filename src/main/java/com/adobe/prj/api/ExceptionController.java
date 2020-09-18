package com.adobe.prj.api;

import java.util.Date;

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

@ControllerAdvice
@RestController
public class ExceptionController extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(ExceptionNotFound.class)
	  public final ResponseEntity<ExceptionMessage> handleUserNotFoundException(ExceptionNotFound ex, WebRequest request) {
		ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
	  }
	
	@ExceptionHandler(ExceptionTokenExpired.class)
	public final ResponseEntity<ExceptionMessage> handleTokenExpiredException(ExceptionTokenExpired ex, WebRequest request) {
		ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
	  }
	
	@ExceptionHandler(CustomException.class)
	  public final ResponseEntity<ExceptionMessage> handleUserNotFoundException(CustomException ex, WebRequest request) {
		ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
	 }
	

}
