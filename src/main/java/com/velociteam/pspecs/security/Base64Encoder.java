package com.velociteam.pspecs.security;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

public class Token {
	private final String username;
	
	public Token(String username) {
		this.username = username;
	}

	public String base64(){
		String keySource = username + String.valueOf(new Date().getTime()) + new SecureRandom().toString();
		return new String(Base64.getEncoder().encode(keySource.getBytes()));
	}

}
