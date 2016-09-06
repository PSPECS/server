package com.velociteam.pspecs.dto;

public class SignupDTO {
	private String nombre;
	private String apellido;
	private String mail;
	private String password;
	private String fnac;
	private String etapaPecs;
	private String rol;
	private String foto;
	
	public SignupDTO() {}
	
	public SignupDTO(String nombre, String apellido, String mail,String password, String fnac, String etapaPecs, String rol,
			String foto) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.mail = mail;
		this.password = password;
		this.fnac = fnac;
		this.etapaPecs = etapaPecs;
		this.rol = rol;
		this.foto = foto;
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
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFnac() {
		return fnac;
	}
	public void setFnac(String fnac) {
		this.fnac = fnac;
	}
	public String getEtapaPecs() {
		return etapaPecs;
	}
	public void setEtapaPecs(String etapaPecs) {
		this.etapaPecs = etapaPecs;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}

}
