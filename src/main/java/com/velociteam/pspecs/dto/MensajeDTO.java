package com.velociteam.pspecs.dto;

import java.util.List;

public class MensajeDTO {
	public String to;
	public List<ImagenMetadataDTO> imagenes;
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public List<ImagenMetadataDTO> getImagenes() {
		return imagenes;
	}
	public void setImagenes(List<ImagenMetadataDTO> imagenes) {
		this.imagenes = imagenes;
	}
	
}
