package com.velociteam.pspecs.exception;

public class BussinessException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BussinessException() {}
	
	public BussinessException(String msg) {
		super(msg);
	}
	
	public BussinessException(String msg, Throwable cause) {
		super(msg,cause);
	}
}
