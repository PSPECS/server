package com.velociteam.pspecs.dto;

public class UpdatedTokensDTO {
	
	private String accessToken;
	private String refreshToken;

	public UpdatedTokensDTO() {
	}
	
	public UpdatedTokensDTO(String newAT, String newRT) {
		this.accessToken=newAT;
		this.refreshToken=newRT;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
