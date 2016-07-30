package com.velociteam.pspecs.dto;

public class UsuariosConectadosDTO {
	
	public Long usuario;
	public Integer mensajesEnviados;
	
	public Long getUsuario() {
		return usuario;
	}
	public void setUsuario(Long idUsuario) {
		this.usuario = idUsuario;
	}
	public Integer getMensajesEnviados() {
		return mensajesEnviados;
	}
	public void setMensajesEnviados(Integer mensajesEnviados) {
		this.mensajesEnviados = mensajesEnviados;
	}

}
