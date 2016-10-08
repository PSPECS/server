package com.velociteam.pspecs.dto;

public class ReportRequestDTO {
	public String paciente;
	public String fechaInicio;
	public String fechaFin;
	public String profesional;
	
	public ReportRequestDTO() {
	}
	public ReportRequestDTO(String paciente, String fechaInicio, String fechaFin, String profesional) {
		this.paciente = paciente;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.profesional = profesional;
	}
	public String getPaciente() {
		return paciente;
	}
	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getProfesional() {
		return profesional;
	}
	public void setProfesional(String profesional) {
		this.profesional = profesional;
	}
}
