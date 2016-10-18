package com.velociteam.pspecs.security;

import java.util.Date;

import com.velociteam.pspecs.exception.AuthenticationException;

public class AccessTokenValidator implements TokenValidator{

	final private Token token;
	final private Base64Decoder decoder;
	
	public AccessTokenValidator(Token token) {
		this.token=token;
		this.decoder=new Base64Decoder(this.token);
	}
	
	@Override
	public void validate() {
		if (diffGTE30Mins(new Date().getTime() - timestamp())){
			throw new AuthenticationException("El access token ingresado expiro.");
		}
	}
	
	private Long timestamp() {
		return Long.valueOf(decoder.decode().split("/")[1]);
	}
	
	private boolean diffGTE30Mins(Long diff) {
		if (diff>(3*60*1000)){
			return true;
		}
		return false; 
	}

}
