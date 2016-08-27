package com.velociteam.pspecs.dto;

public class ImagenMetadataDTO{
	private String id;
	private String tipo;
	public ImagenMetadataDTO(String id, String tipo) {
		super();
		this.id = id;
		this.tipo = tipo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
