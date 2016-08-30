package com.velociteam.pspecs.exception;

public class MongoException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MongoException() {}
	
	public MongoException(String msg) {
		super(msg);
	}
	
	public MongoException(String msg, Throwable cause) {
		super(msg,cause);
	}
	
}
