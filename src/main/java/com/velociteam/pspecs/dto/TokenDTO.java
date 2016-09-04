package com.velociteam.pspecs.dto;

public class TokenDTO {
	
	private String refreshToken;

	public TokenDTO(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public TokenDTO() {}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String token) {
		this.refreshToken = token;
	}

}
