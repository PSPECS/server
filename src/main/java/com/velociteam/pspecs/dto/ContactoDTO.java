package com.velociteam.pspecs.dto;

import org.bson.types.ObjectId;

import com.mongodb.DBObject;

public class ContactoDTO {
	
	private String userId;
	private String nombre;
	private String profilePic;
	
	public ContactoDTO() {
	}
	
	public ContactoDTO(DBObject dbObject) {
		this.userId = ((ObjectId) dbObject.get("_id")).toString();
		this.nombre = (String) dbObject.get("nombre")+" "+(String) dbObject.get("apellido");
		this.profilePic = (String) dbObject.get("imagenDePerfil");;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

}
