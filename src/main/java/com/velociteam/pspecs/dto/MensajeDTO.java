package com.velociteam.pspecs.dto;

import java.util.List;

public class MensajeDTO {
	public String to;
	public List<String> imagenes;
	
	public String getUsuarioDestino() {
		return to;
	}
	public void setUsuarioDestino(String usuarioDestino) {
		this.to = usuarioDestino;
	}
	public List<String> getImagenes() {
		return imagenes;
	}
	public void setImagenes(List<String> imagenes) {
		this.imagenes = imagenes;
	}
	
}
