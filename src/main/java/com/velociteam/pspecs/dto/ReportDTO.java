package com.velociteam.pspecs.dto;

import java.util.List;
import java.util.Map;

import com.velociteam.pspecs.services.Tuple;

public class ReportDTO {
	
	Map<String,Double> tiemposDeUso;
	List<Tuple> usuariosContactados;
	List<Tuple> pictogramasMasUtilizados;

	public ReportDTO() {
	}
	
	public ReportDTO(Map<String, Double> tiemposDeUso,List<Tuple> usuariosContactados,List<Tuple> pictogramasMasUtilizados) {
		this.tiemposDeUso = tiemposDeUso;
		this.usuariosContactados = usuariosContactados;
		this.pictogramasMasUtilizados = pictogramasMasUtilizados;
	}
	
	public Map<String, Double> getTiemposDeUso() {
		return tiemposDeUso;
	}

	public List<Tuple> getUsuariosContactados() {
		return usuariosContactados;
	}

	public List<Tuple> getPictogramasMasUtilizados() {
		return pictogramasMasUtilizados;
	}

}
