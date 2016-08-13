package com.velociteam.pspecs.dto;

import java.util.List;

public class MensajeDTO {
	public String to;
	public List<String> imagenes;
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public List<String> getImagenes() {
		return imagenes;
	}
	public void setImagenes(List<String> imagenes) {
		this.imagenes = imagenes;
	}
	
	
}
