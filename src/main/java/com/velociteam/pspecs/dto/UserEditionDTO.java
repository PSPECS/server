package com.velociteam.pspecs.dto;

public class UserEditionDTO {
	private String name;
	private String lastname;
	private String email;
	private String birthDate;
	private String pecsLevel;
	private String selectedProfilePic;
	
	public UserEditionDTO(){
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getPecsLevel() {
		return pecsLevel;
	}
	public void setPecsLevel(String pecsLevel) {
		this.pecsLevel = pecsLevel;
	}
	public String getSelectedProfilePic() {
		return selectedProfilePic;
	}
	public void setSelectedProfilePic(String selectedProfilePic) {
		this.selectedProfilePic = selectedProfilePic;
	}

}
