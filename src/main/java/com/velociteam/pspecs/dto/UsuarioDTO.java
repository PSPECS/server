package com.velociteam.pspecs.dto;

import java.util.Optional;

import com.mongodb.DBObject;

public class UsuarioDTO {
	
	public String id;
	public String nombre;
	public String apellido;
	public String etapaPecs;
	public String imagenDePerfil;
	public String nuevosMensajes;
	
	public UsuarioDTO() {}
	
	public UsuarioDTO(DBObject dbObject) {
		Optional<String> imagenPerfil = Optional.of((String) dbObject.get("imagenDePerfil"));
		this.id = (String) dbObject.get("_id");
		this.nombre = (String) dbObject.get("nombre");
		this.apellido = (String) dbObject.get("apellido");
		this.etapaPecs = (String) dbObject.get("etapaPecs");
		this.imagenDePerfil = imagenPerfil.orElse("");
		this.nuevosMensajes = "false";
	}
	
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
	public String getNuevosMensajes() {
		return nuevosMensajes;
	}
	public void setNuevosMensajes(String nuevosMensajes) {
		this.nuevosMensajes = nuevosMensajes;
	}
	

}
