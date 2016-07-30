package com.velociteam.pspecs.dto;

import java.util.List;

public class LogDTO {

//	{ 
//	    "dtInicio": "datetime",
//		"dtFin": "datetime",
//		"usosOk": 99999,
//		"usosNoOk": 99999,
//		"userId": 1,
//		"pictogramas": [
//			{
//				"nombre": "nombrePictograma",
//	    		"categoria": "nombreCategoria",
//				"usos": 99999
//			}
//		]
//		usuariosConectados:[
//			{
//				userId: 1,
//				mensajesEnviados:99
//			}
//		]
//		
//	}
	
	private Long userId;
	private String dtInicio;
	private String dtFin;
	private Integer usosOk;
	private Integer usosNoOk;
	private List<PictogramaDTO> pictogramas;
	private List<UsuariosContactadosDTO> usuariosContactados;
	
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
	public List<PictogramaDTO> getPictogramas() {
		return pictogramas;
	}
	public void setPictogramas(List<PictogramaDTO> pictogramas) {
		this.pictogramas = pictogramas;
	}
	public List<UsuariosContactadosDTO> getUsuariosContactados() {
		return usuariosContactados;
	}
	public void setUsuariosContactados(List<UsuariosContactadosDTO> usuariosContactados) {
		this.usuariosContactados = usuariosContactados;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
