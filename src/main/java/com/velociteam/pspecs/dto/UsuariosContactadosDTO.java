package com.velociteam.pspecs.dto;

public class UsuariosContactadosDTO {
	
	public Long userId;
	public Integer mensajesEnviados;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getMensajesEnviados() {
		return mensajesEnviados;
	}
	public void setMensajesEnviados(Integer mensajesEnviados) {
		this.mensajesEnviados = mensajesEnviados;
	}

}
