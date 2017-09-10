package com.velociteam.pspecs.dto;

public class CommonTokenDTO {
	
	private String token;

	public CommonTokenDTO(String token) {
		this.token = token;
	}
	public CommonTokenDTO() {}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
