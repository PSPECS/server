package com.velociteam.pspecs.security;

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
		if (timestamp()>=plus30Mins(timestamp())){
			throw new AuthenticationException("El access token ingresado expiro.");
		}
	}
	
	private Long timestamp() {
		return Long.valueOf(decoder.decode().split("/")[1]);
	}
	
	private long plus30Mins(Long timestamp) {
		return timestamp*1*60*1000;
	}

}
