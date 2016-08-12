package com.velociteam.pspecs.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.velociteam.pspecs.dto.TokenDTO;
import com.velociteam.pspecs.dto.UsuarioDTO;

@Repository
public class UsuariosDao extends AbstractDao{

	public List<UsuarioDTO> getContacts(String userId) {
		List<UsuarioDTO> contactos = new ArrayList<>();
		
		DBCursor dbUsuario = getDB().getCollection("usuario")
				.find(new BasicDBObject("_id",new ObjectId(userId)),new BasicDBObject("contactos",1));
		
		for (DBObject usuario : dbUsuario) {
			BasicDBList contacts = (BasicDBList) usuario.get("contactos");
			
			for (Object contact : contacts) {
				contactos.add(fromDBtoDTO(contact));
			}
		}
		return contactos;
	}


	public void updateToken(String userId, TokenDTO tokenDTO) {
		getDB().getCollection("usuario").update(new BasicDBObject("_id",new ObjectId(userId)), 
				new BasicDBObject("$set",new BasicDBObject("token",tokenDTO.getRefreshToken())));
	}

	public String getTokenByUser(String userId) {
		DBObject token = getDB().getCollection("usuario")
				.findOne(new BasicDBObject("_id",new ObjectId(userId)),new BasicDBObject("token",1));
		return token.get("token").toString();
	}

	public UsuarioDTO getUserInfoById(String userId) {
		DBCursor dbUsuario = getDB().getCollection("usuario")
				.find(new BasicDBObject("_id",new ObjectId(userId)));
		
		return fromDBtoDTO(dbUsuario);
	}
	
	private UsuarioDTO fromDBtoDTO(Object dbObject) {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setUsuario((String) ((DBObject) dbObject).get("username"));
		usuarioDTO.setNombre((String) ((DBObject) dbObject).get("nombre"));
		usuarioDTO.setApellido((String) ((DBObject) dbObject).get("apellido"));
		usuarioDTO.setEtapaPecs((String) ((DBObject) dbObject).get("etapaPecs"));
		usuarioDTO.setImagenDePerfil((String) ((DBObject) dbObject).get("imagenPerfile"));
		return usuarioDTO;
	}

}
