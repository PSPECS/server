package com.velociteam.pspecs.dao;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.velociteam.pspecs.exception.AuthenticationException;
import com.velociteam.pspecs.security.AccessTokenValidator;
import com.velociteam.pspecs.security.Base64Decoder;
import com.velociteam.pspecs.security.Base64Encoder;
import com.velociteam.pspecs.security.RefreshTokenValidator;
import com.velociteam.pspecs.security.Token;
import com.velociteam.pspecs.security.TokenBuilder;

public class TestAccessToken {
	String name;
	Long now;
	
//	@Test(expected=AuthenticationException.class)
//	public void testATokenInvalid(){
//		new AccessTokenValidator(new Token("mZmLzE0NzM4MTMyNjMxMTkvamF2YS5zZWN1cml0eS5TZWN1cmVSYW5kb21AMTA2OWU3",true)).validate();
//	}
//	
//	@Test(expected=AuthenticationException.class)
//	public void testRTokenInvalid(){
//		new RefreshTokenValidator(new Token("UmVndWxhcjUvMTQ3MzgwNjcwOTEyNS9qYXZhLnNlY3VyaXR5LlNlY3VyZVJhbmRvbUAzNGFhMGQ=",true)).validate();
//	}
//	
	@Before 
	public void setUp() throws InterruptedException{
	    name ="mtest";
		now = new Date().getTime();
		Thread.sleep(100);
		System.out.println(String.valueOf(now));
	}
	
	@Test
	public void shouldGetValidRT(){
		Token token = new TokenBuilder(name).asRT().encode().build();
		shouldDecodeTokenSuccesfully(token);
	}
	
	@Test
	public void shouldGetValidAT(){
		Token token = new TokenBuilder(name).asAT().encode().build();
		shouldDecodeTokenSuccesfully(token);
	}
	
	@Test
	public void shouldGetValidRT1(){
		Token token = new Token("bXRlc3QvMTQ3NTkzOTY0MjEwMS8xNjQ2ODI2",false);
		shouldDecodeTokenSuccesfully(token);
	}
	
	
	private void shouldDecodeTokenSuccesfully(Token token) {
		System.out.println(token.toString());
		
		final String tokenDecoded = new Base64Decoder(token).decode();
		
		System.out.println(tokenDecoded);
		
		Assert.assertEquals(name, tokenDecoded.split("/")[0]);
//		Assert.assertTrue(Long.valueOf(tokenDecoded.split("/")[1]).longValue()>now.longValue());
	}

	
}
