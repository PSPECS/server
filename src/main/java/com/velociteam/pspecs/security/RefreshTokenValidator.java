package com.velociteam.pspecs.security;

import com.velociteam.pspecs.exception.AuthenticationException;

public class RefreshTokenValidator implements TokenValidator {

	final private Token token;
	final private Base64Decoder decoder;
	
	public RefreshTokenValidator(Token token) {
		this.token=token;
		this.decoder=new Base64Decoder(this.token);
	}
	
	@Override
	public void validate() {
		if (timestamp()>=plus1Week(timestamp())){
			throw new AuthenticationException("El refresh token ingresado expiro.");
		}
	}
	
	private Long timestamp() {
		return Long.valueOf(decoder.decode().split("/")[1]);
	}
	
	private Long plus1Week(Long timestamp) {
		return timestamp*7*24*60*60*1000;
	}
}
