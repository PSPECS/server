package com.velociteam.pspecs.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.velociteam.pspecs.dto.SignupDTO;
import com.velociteam.pspecs.dto.TokenDTO;
import com.velociteam.pspecs.dto.UsuarioDTO;
import com.velociteam.pspecs.exception.BussinessException;

@Repository
public class UsuariosDao extends AbstractDao{

	@Autowired
	public UsuariosDao(MongodbDBCreator aCreator) {
		super(aCreator);
	}

	public List<UsuarioDTO> getContacts(String userId) {
		List<UsuarioDTO> contactos = new ArrayList<>();
		
		DBCursor dbUsuario = collection("usuario")
				.find(new BasicDBObject("_id",new ObjectId(userId)),new BasicDBObject("contactos",1));
		
		for (DBObject usuario : dbUsuario) {
			BasicDBList contacts = (BasicDBList) usuario.get("contactos");
			if (contacts==null) continue;
			for (Object contact : contacts) {
				contactos.add(fromDBtoDTO((DBObject)contact));
			}
		}
		return contactos;
	}


	public void updateToken(String userId, TokenDTO tokenDTO) {
		collection("usuario").update(new BasicDBObject("_id",new ObjectId(userId)), 
				new BasicDBObject("$set",new BasicDBObject("token",tokenDTO.getRefreshToken())));
	}

	public String getTokenByUser(String userId) {
		DBObject token = collection("usuario")
				.findOne(new BasicDBObject("_id",new ObjectId(userId)),new BasicDBObject("token",1));
		return token.get("token").toString();
	}

	public UsuarioDTO getUserInfoById(String userId) {
		DBObject dbUsuario = collection("usuario")
				.find(new BasicDBObject("_id",new ObjectId(userId))).one();
		
		return fromDBtoDTO(dbUsuario);
	}
	
	public UsuarioDTO getUserInfoByNyA(String nombre,String apellido) {
		DBObject dbUsuario = collection("usuario")
				.find(new BasicDBObject("nombre",nombre)
						.append("apellido", apellido)).one();
		
		return fromDBtoDTO(dbUsuario);
	}
	
	public void createUser(SignupDTO signupDTO) throws ParseException{
		collection("usuario").insert(new BasicDBObject("nombre",signupDTO.getNombre())
				.append("apellido", signupDTO.getApellido())
				.append("mail", signupDTO.getMail())
				.append("fnac", new SimpleDateFormat("dd/MM/yyyy").parse(signupDTO.getFnac()).getTime())
				.append("etapaPecs", signupDTO.getEtapaPecs())
				.append("rol", signupDTO.getRol())
				.append("imagenDePerfil",signupDTO.getFoto())
//				TODO agregar la generacion de los tokens
				.append("accesToken", "")
				.append("refreshToken", ""));
	}
	
	public ObjectId isValid(String userId) {
		final DBCursor cursor = collection("usuario").find(new BasicDBObject("_id",new ObjectId(userId)));
		if (cursor.size()<=0){
			throw new BussinessException("El usuario ingresado no existe.");
		}
		return new ObjectId(userId);
	}
	
	private UsuarioDTO fromDBtoDTO(DBObject dbObject) {
		Optional<String> imagenPerfil = Optional.of((String) dbObject.get("imagenDePerfil"));
		return new UsuarioDTO(((ObjectId) dbObject.get("_id")).toString(),
				(String) dbObject.get("nombre"),
				(String) dbObject.get("apellido"),
				(String) dbObject.get("etapaPecs"),
				imagenPerfil.orElse(""),
				"false");
	}

}
