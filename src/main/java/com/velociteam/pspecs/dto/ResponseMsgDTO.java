package com.velociteam.pspecs.dto;

import java.util.List;

public class ResponseMsgDTO {
	
	private String enviadoPor;
	private String timestamp;
	private List<ImagenMetadataDTO> imagenes;
	
	public ResponseMsgDTO() {
	}
	
	public ResponseMsgDTO(String enviadoPor, String timestamp, List<ImagenMetadataDTO> imagenes) {
		super();
		this.enviadoPor = enviadoPor;
		this.timestamp = timestamp;
		this.imagenes = imagenes;
	}
	
	public String getEnviadoPor() {
		return enviadoPor;
	}
	public void setEnviadoPor(String enviadoPor) {
		this.enviadoPor = enviadoPor;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public List<ImagenMetadataDTO> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<ImagenMetadataDTO> imagenes) {
		this.imagenes = imagenes;
	}
	

}
