package com.velociteam.pspecs.rest;

import org.springframework.beans.factory.annotation.Autowired;

import com.velociteam.pspecs.exception.AuthenticationException;
import com.velociteam.pspecs.security.Token;
import com.velociteam.pspecs.services.AuthenticationService;

public abstract class AbstractResource {
	
	@Autowired
	private AuthenticationService authService;
	
	public void auth(String autHeader){
		if (autHeader!=null){
			String token = autHeader.replace("Basic ", "");
			authService.isValidAccessToken(new Token(token,true));
		} else{
			throw new AuthenticationException("Acceso no autorizado.");
		}
	}

}
