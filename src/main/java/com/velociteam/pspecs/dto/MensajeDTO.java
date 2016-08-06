package com.velociteam.pspecs.dto;

import java.util.List;

public class MensajeDTO {
	public String usuarioDestino;
	public String timestamp;
	public List<ImagenDTO> imagenes;
	
	public String getUsuarioDestino() {
		return usuarioDestino;
	}
	public void setUsuarioDestino(String usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public List<ImagenDTO> getImagenes() {
		return imagenes;
	}
	public void setImagenes(List<ImagenDTO> imagenes) {
		this.imagenes = imagenes;
	}
	
}
