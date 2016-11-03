package com.velociteam.pspecs.dto;

import java.util.Optional;

import org.bson.types.ObjectId;

import com.mongodb.DBObject;

public class CredentialsResponseDTO {
	private String refreshToken;
	private String accessToken;
	private String id;
	private String nombre;
	private String fnac;
	private String apellido;
	private String mail;
	private String rol;
	private String etapaPecs;
	private String imagenDePerfil;
	
	public CredentialsResponseDTO() {}
	
	public CredentialsResponseDTO(DBObject dbObject) {
		this.accessToken = (String) dbObject.get("accessToken");
		this.refreshToken = (String) dbObject.get("refreshToken");
		this.id = ((ObjectId) dbObject.get("_id")).toString();
		this.nombre = (String) dbObject.get("nombre");
		this.apellido = (String) dbObject.get("apellido");
		this.mail = (String) dbObject.get("mail");
		this.rol = (String) dbObject.get("rol");
		this.etapaPecs = (String) dbObject.get("etapaPecs");
		this.imagenDePerfil = (String) dbObject.get("imagenDePerfil");
		this.fnac = (String) dbObject.get("fnac");
	}
	public String getImagenDePerfil() {
		return imagenDePerfil;
	}
	public void setImagenDePerfil(String imagenDePerfil) {
		this.imagenDePerfil = imagenDePerfil;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getEtapaPecs() {
		return etapaPecs;
	}

	public void setEtapaPecs(String etapaPecs) {
		this.etapaPecs = etapaPecs;
	}

	public String getFnac() {
		return fnac;
	}

	public void setFnac(String fnac) {
		this.fnac = fnac;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
}
