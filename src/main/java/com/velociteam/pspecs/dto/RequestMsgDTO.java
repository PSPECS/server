package com.velociteam.pspecs.dto;

public class RequestMsgDTO {
	private String usuarioAChatear;
	private String anteriorA;
	private String ultimos;
	
	public String getUsuarioAChatear() {
		return usuarioAChatear;
	}
	public void setUsuarioAChatear(String usuarioAChatear) {
		this.usuarioAChatear = usuarioAChatear;
	}
	public String getAnteriorA() {
		return anteriorA;
	}
	public void setAnteriorA(String anteriorA) {
		this.anteriorA = anteriorA;
	}
	public String getUltimos() {
		return ultimos;
	}
	public void setUltimos(String ultimos) {
		this.ultimos = ultimos;
	}
}
