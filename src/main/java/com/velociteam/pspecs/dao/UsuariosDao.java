package com.velociteam.pspecs.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.velociteam.pspecs.dto.TokenDTO;
import com.velociteam.pspecs.dto.UsuarioDTO;

@Repository
public class UsuariosDao {

	public List<UsuarioDTO> getContacts(String userId) {
		List<UsuarioDTO> contactos = new ArrayList<>();
		
		DBCursor dbUsuario = getDB().getCollection("usuario")
				.find(new BasicDBObject("_id",new ObjectId(userId)),new BasicDBObject("contactos",1));
		
		for (DBObject usuario : dbUsuario) {
			BasicDBList contacts = (BasicDBList) usuario.get("contactos");
			
			for (Object contact : contacts) {
				UsuarioDTO usuarioDTO = new UsuarioDTO();
				usuarioDTO.setUsuario((String) ((DBObject) contact).get("username"));
				usuarioDTO.setNombre((String) ((DBObject) contact).get("nombre"));
				usuarioDTO.setApellido((String) ((DBObject) contact).get("apellido"));
				usuarioDTO.setImagenDePerfil((String) ((DBObject) contact).get("imagenPerfile"));
				contactos.add(usuarioDTO);
			}
		}
		return contactos;
	}

	public void updateToken(String userId, TokenDTO tokenDTO) {
		getDB().getCollection("usuario").update(new BasicDBObject("_id",new ObjectId(userId)), 
				new BasicDBObject("$set",new BasicDBObject("token",tokenDTO.getRefreshToken())));
		
	}

	private DB getDB() {
		DB db = null;
		try {
			db = new MongoClient(new MongoClientURI("mongodb://admin:uNckDSYqc-FL@127.8.107.130:27017/")).getDB("pspecs");
		} catch (UnknownHostException e) {
			throw new RuntimeException(e.getCause());
		}
		return db;
	}

	public String getTokenByUser(String userId) {
		DBObject token = getDB().getCollection("usuario")
				.findOne(new BasicDBObject("_id",new ObjectId(userId)),new BasicDBObject("token",1));
		return token.get("token").toString();
	}

}
