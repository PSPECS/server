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
}
