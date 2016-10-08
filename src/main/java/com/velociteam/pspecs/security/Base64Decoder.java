package com.velociteam.pspecs.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Decoder {
	
	final private String token;

	public Base64Decoder(Token token) {
		this.token = token.toString();
	}

	public String decode(){
		return new String(Base64.getDecoder().decode(token.getBytes(StandardCharsets.UTF_8)));
	}

}
