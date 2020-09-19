package com.adobe.prj.exception;

import org.springframework.security.core.AuthenticationException;

public class ExceptionTokenExpired extends AuthenticationException{

	public ExceptionTokenExpired(String msg) {
		super(msg);
	}
}
