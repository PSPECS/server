package com.velociteam.pspecs.dto;

public class UsuarioDTO {
	
	public String id;
	public String nombre;
	public String apellido;
	public String etapaPecs;
	public String imagenDePerfil;
	public Boolean nuevosMensajes;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEtapaPecs() {
		return etapaPecs;
	}
	public void setEtapaPecs(String etapaPecs) {
		this.etapaPecs = etapaPecs;
	}
	public String getImagenDePerfil() {
		return imagenDePerfil;
	}
	public void setImagenDePerfil(String imagenDePerfil) {
		this.imagenDePerfil = imagenDePerfil;
	}
	public Boolean getNuevosMensajes() {
		return nuevosMensajes;
	}
	public void setNuevosMensajes(Boolean nuevosMensajes) {
		this.nuevosMensajes = nuevosMensajes;
	}
	

}
