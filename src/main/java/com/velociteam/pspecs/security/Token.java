package com.velociteam.pspecs.security;

public class Token {
	
	final private String token;
	final private TokenValidator validator;
	
	public Token(String token,boolean isATValidator) {
		this.token = token;
		this.validator = (isATValidator)?new AccessTokenValidator(this):new RefreshTokenValidator(this);
	}
	
	public void isValid(){
		validator.validate();
	}
	
	@Override
	public String toString(){
		return this.token;
	}

}
