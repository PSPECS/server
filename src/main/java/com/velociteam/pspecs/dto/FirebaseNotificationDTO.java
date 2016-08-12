package com.velociteam.pspecs.dto;

public class FirebaseNotificationDTO {
	private String body;
	private String title;
	private String icon;
	private String sound;
	private String click_action;
	
	public FirebaseNotificationDTO() {
	}
	
	
	public FirebaseNotificationDTO(String body, String title, String icon, String sound, String click_action) {
		super();
		this.body = body;
		this.title = title;
		this.icon = icon;
		this.sound = sound;
		this.click_action = click_action;
	}
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getClick_action() {
		return click_action;
	}
	public void setClick_action(String click_action) {
		this.click_action = click_action;
	}
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
}
