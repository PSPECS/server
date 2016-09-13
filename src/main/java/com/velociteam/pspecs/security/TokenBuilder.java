package com.velociteam.pspecs.security;

public class TokenBuilder {
	
	final private String nombre;
	private Base64Encoder encoder;
	private Boolean accessToken=true;

	public TokenBuilder(String nombre) {
		this.nombre = nombre;
	}
	
	public TokenBuilder encode(){
		this.encoder = new Base64Encoder(this.nombre);
		return this;
	}
	
	public TokenBuilder asAT(){
		this.accessToken=true;
		return this;
	}
	
	public TokenBuilder asRT(){
		this.accessToken=false;
		return this;
	}
	
	public Token build(){
		return new Token(encoder.encode(),this.accessToken);
	}
	
}
