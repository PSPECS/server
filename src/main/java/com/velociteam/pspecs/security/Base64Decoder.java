package com.velociteam.pspecs.security;

import java.util.Base64;

public class Base64Decoder {
	
	final private String token;

	public Base64Decoder(Token token) {
		this.token = token.toString();
	}

	public String decode(){
		return Base64.getDecoder().decode(token).toString();
	}

}
