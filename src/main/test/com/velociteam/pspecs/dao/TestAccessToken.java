package com.velociteam.pspecs.dao;

import org.junit.Test;

import com.velociteam.pspecs.exception.AuthenticationException;
import com.velociteam.pspecs.security.AccessTokenValidator;
import com.velociteam.pspecs.security.RefreshTokenValidator;
import com.velociteam.pspecs.security.Token;

public class TestAccessToken {
	
	@Test(expected=AuthenticationException.class)
	public void testATokenInvalid(){
		new AccessTokenValidator(new Token("mZmLzE0NzM4MTMyNjMxMTkvamF2YS5zZWN1cml0eS5TZWN1cmVSYW5kb21AMTA2OWU3",true)).validate();
	}
	
	@Test(expected=AuthenticationException.class)
	public void testRTokenInvalid(){
		new RefreshTokenValidator(new Token("UmVndWxhcjUvMTQ3MzgwNjcwOTEyNS9qYXZhLnNlY3VyaXR5LlNlY3VyZVJhbmRvbUAzNGFhMGQ=",true)).validate();
	}

}
