package com.velociteam.pspecs.dto;

import java.util.Map;

public class ReportDTO {
	
	Map<String,Long> tiemposDeUso;

	public ReportDTO() {
	}

	public Map<String, Long> getTiemposDeUso() {
		return tiemposDeUso;
	}

	public void setTiemposDeUso(Map<String, Long> tiemposDeUso) {
		this.tiemposDeUso = tiemposDeUso;
	}

}
