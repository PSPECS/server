package com.velociteam.pspecs.dto;

public class UsuariosContactadosDTO {
	
	public String userId;
	public Integer mensajesEnviados;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getMensajesEnviados() {
		return mensajesEnviados;
	}
	public void setMensajesEnviados(Integer mensajesEnviados) {
		this.mensajesEnviados = mensajesEnviados;
	}

}
