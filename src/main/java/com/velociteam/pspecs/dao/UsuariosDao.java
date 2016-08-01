package com.velociteam.pspecs.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.BasicBSONList;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.velociteam.pspecs.dto.UsuarioDTO;

@Repository
public class UsuariosDao {

	public List<UsuarioDTO> getContacts(String userId) throws UnknownHostException {
		List<UsuarioDTO> contactos = new ArrayList<>();
		
		final DB db = new MongoClient(new MongoClientURI("mongodb://admin:uNckDSYqc-FL@127.8.107.130:27017/")).getDB("pspecs");
		DBObject dbUsuario = db.getCollection("usuario")
				.findOne(new BasicDBObject("_id",new ObjectId(userId)),new BasicDBObject("contactos",1));
		
		BasicDBList contacts = (BasicDBList) dbUsuario.get("contacts");
		for (Object contact : contacts) {
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setUsuario((String) ((DBObject) contact).get("username"));
			usuarioDTO.setNombre((String) ((DBObject) contact).get("nombre"));
			usuarioDTO.setApellido((String) ((DBObject) contact).get("apellido"));
			usuarioDTO.setImagenDePerfil((String) ((DBObject) contact).get("imagenPerfile"));
			contactos.add(usuarioDTO);
		}
		return contactos;
	}

}
