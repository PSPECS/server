package com.velociteam.pspecs.dto;

import java.util.List;
import java.util.Map;

import com.velociteam.pspecs.services.Tuple;

public class ReportDTO {
	
	Map<String,Double> tiemposDeUso;
	Map<String,List<Tuple>> usuariosContactados;
	Map<String,List<Tuple>> pictogramasMasUtilizados;

	public ReportDTO() {
	}
	
	public ReportDTO(Map<String, Double> tiemposDeUso, Map<String, List<Tuple>> usuariosContactados,Map<String,List<Tuple>> pictogramasMasUtilizados) {
		this.tiemposDeUso = tiemposDeUso;
		this.usuariosContactados = usuariosContactados;
		this.pictogramasMasUtilizados = pictogramasMasUtilizados;
	}
	
	public Map<String, Double> getTiemposDeUso() {
		return tiemposDeUso;
	}

	public Map<String, List<Tuple>> getUsuariosContactados() {
		return usuariosContactados;
	}

	public Map<String, List<Tuple>> getPictogramasMasUtilizados() {
		return pictogramasMasUtilizados;
	}

}
