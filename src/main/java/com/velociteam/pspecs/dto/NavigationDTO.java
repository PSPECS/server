package com.velociteam.pspecs.dto;

import java.util.Date;
import java.util.List;

public class NavigationDTO {
	
	private Date dtInicio;
	private Date dtFin;
	private Integer usosOk;
	private Integer usosNoOk;
	private String usuario;
	private List<PictogramaDTO> pictogramas;
	
	public Date getDtInicio() {
		return dtInicio;
	}
	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}
	public Date getDtFin() {
		return dtFin;
	}
	public void setDtFin(Date dtFin) {
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
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public List<PictogramaDTO> getPictogramas() {
		return pictogramas;
	}
	public void setPictogramas(List<PictogramaDTO> pictogramas) {
		this.pictogramas = pictogramas;
	}

}
