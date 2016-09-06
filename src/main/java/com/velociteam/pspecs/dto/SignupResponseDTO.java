package com.velociteam.pspecs.dto;

import org.bson.types.ObjectId;

import com.mongodb.DBObject;

public class SignupResponseDTO {
	
	private String id;
	private String accessToken;
	private String refreshToken;
	
	public SignupResponseDTO() {}
	
	public SignupResponseDTO(DBObject dbObject) {
		this.id = ((ObjectId) dbObject.get("_id")).toString();
		this.accessToken = (String) dbObject.get("accesToken");
		this.refreshToken = (String) dbObject.get("refreshToken");
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
