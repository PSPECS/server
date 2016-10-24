package com.velociteam.pspecs.dto;

public class PassEditionDTO {
	
	private String oldPassword;
	private String password;
	
	public PassEditionDTO(){}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

}
