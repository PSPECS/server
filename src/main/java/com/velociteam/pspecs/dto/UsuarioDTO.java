package com.velociteam.pspecs.dto;

import com.mongodb.DBObject;

public class UsuarioDTO {
	
	public String id;
	public String nombre;
	public String apellido;
	public String etapaPecs;
	public String imagenDePerfil;
	public String nuevosMensajes;
	public String email; 
	public String rol;
	
	public UsuarioDTO() {}
	
	public UsuarioDTO(DBObject dbObject) {
		this.id = (String) dbObject.get("_id").toString();
		this.nombre = (String) dbObject.get("nombre");
		this.apellido = (String) dbObject.get("apellido");
		this.etapaPecs = (String) dbObject.get("etapaPecs");
		this.email = (String) dbObject.get("mail");
		this.imagenDePerfil = (String) dbObject.get("imagenDePerfil");
		this.rol = (String) dbObject.get("rol");
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	

}
