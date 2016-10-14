package com.velociteam.pspecs.dao;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.velociteam.pspecs.security.Token;
import com.velociteam.pspecs.security.TokenBuilder;

public class TestAccessToken {
	String name;
	Long now;
	
	@Before 
	public void setUp() throws InterruptedException{
	    name ="mtest";
		now = new Date().getTime();
		Thread.sleep(100);
//		System.out.println(String.valueOf(now));
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
	public void test(){
		System.out.println(0+(double) 40270L/(1000*60*60));
	}
	
	private void shouldDecodeTokenSuccesfully(Token token) {
		System.out.println(token.toString());
		token.isValid();
	}

	
}
