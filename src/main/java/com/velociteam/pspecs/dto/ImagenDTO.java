package com.velociteam.pspecs.dto;

public class ImagenDTO {
	public String resourceId;

	public ImagenDTO(String resourceId) {
		super();
		this.resourceId = resourceId;
	}

	public ImagenDTO() {
	}


	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
}
