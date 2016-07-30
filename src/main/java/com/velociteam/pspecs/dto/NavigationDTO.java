package com.velociteam.pspecs.dto;

import java.util.List;

public class NavigationDTO {

//	{ 
//	    "dtInicio": "datetime",
//		"dtFin": "datetime",
//		"usosOk": 99999,
//		"usosNoOk": 99999,
//		"usuario": "username",
//		"pictogramas": [
//			{
//				"nombre": "nombrePictograma",
//	    		"categoria": "nombreCategoria",
//				"usos": 99999
//			}
//		]
//		usuariosConectados:[
//			{
//				usuario:"username",
//				mensajesEnviados:99
//			}
//		]
//		
//	}
	
	private String dtInicio;
	private String dtFin;
	private Integer usosOk;
	private Integer usosNoOk;
	private Long usuario;
	private List<PictogramaDTO> pictogramas;
	private List<UsuariosConectadosDTO> usuariosConectados;
	
	public String getDtInicio() {
		return dtInicio;
	}
	public void setDtInicio(String dtInicio) {
		this.dtInicio = dtInicio;
	}
	public String getDtFin() {
		return dtFin;
	}
	public void setDtFin(String dtFin) {
		this.dtFin = dtFin;
	}
	public Integer getUsosOk() {
		return usosOk;
	}
	public void setUsosOk(Integer usosOk) {
		this.usosOk = usosOk;
	}
	public Integer getUsosNoOk() {
		return usosNoOk;
	}
	public void setUsosNoOk(Integer usosNoOk) {
		this.usosNoOk = usosNoOk;
	}
	public Long getUsuario() {
		return usuario;
	}
	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}
	public List<PictogramaDTO> getPictogramas() {
		return pictogramas;
	}
	public void setPictogramas(List<PictogramaDTO> pictogramas) {
		this.pictogramas = pictogramas;
	}
	public List<UsuariosConectadosDTO> getUsuariosConectados() {
		return usuariosConectados;
	}
	public void setUsuariosConectados(List<UsuariosConectadosDTO> usuariosConectados) {
		this.usuariosConectados = usuariosConectados;
	}

}
