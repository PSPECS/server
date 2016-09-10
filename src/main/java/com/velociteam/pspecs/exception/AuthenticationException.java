package com.velociteam.pspecs.exception;

public class AuthenticationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AuthenticationException() {}
	
	public AuthenticationException(String msg) {
		super(msg);
	}
	
	public AuthenticationException(String msg, Throwable cause) {
		super(msg,cause);
	}

}
