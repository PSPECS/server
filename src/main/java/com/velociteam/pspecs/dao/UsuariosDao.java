package com.velociteam.pspecs.dao;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.velociteam.pspecs.dto.UsuarioDTO;

public class UsuariosDao {

	public UsuarioDTO getContacts(String userId) throws UnknownHostException {
		final DB db = new MongoClient(new MongoClientURI("mongodb://admin:uNckDSYqc-FL@127.8.107.130:27017/")).getDB("pspecs");
		DBCursor DBContactos = db.getCollection("usuario").find(new BasicDBObject("_id",new ObjectId(userId))).getCollection().getCollection("contactos").find();
		
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		for (DBObject contact : DBContactos) {
			usuarioDTO.setUsuario((String) contact.get("username"));
			usuarioDTO.setNombre((String) contact.get("nombre"));
			usuarioDTO.setApellido((String) contact.get("apellido"));
			usuarioDTO.setImagenDePerfil((String) contact.get("imagenPerfil"));
		}
		return usuarioDTO;
		
	}

}
