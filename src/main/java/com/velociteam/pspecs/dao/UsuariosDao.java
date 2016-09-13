package com.velociteam.pspecs.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.velociteam.pspecs.dto.CredentialsResponseDTO;
import com.velociteam.pspecs.dto.SignupDTO;
import com.velociteam.pspecs.dto.SignupResponseDTO;
import com.velociteam.pspecs.dto.TokenDTO;
import com.velociteam.pspecs.dto.UsuarioDTO;
import com.velociteam.pspecs.exception.AuthenticationException;
import com.velociteam.pspecs.exception.BussinessException;
import com.velociteam.pspecs.security.Token;
import com.velociteam.pspecs.security.TokenBuilder;

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
				contactos.add(new UsuarioDTO((DBObject)contact));
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
		
		return new UsuarioDTO(dbUsuario);
	}
	
	public SignupResponseDTO getUserInfoByNyA(String nombre,String apellido) {
		DBObject dbUsuario = collection("usuario")
				.find(new BasicDBObject("nombre",nombre).append("apellido", apellido)).one();
		
		return new SignupResponseDTO(dbUsuario);
	}
	
	public CredentialsResponseDTO getUserInfoByMailYPass(String mail, String password) {
		DBObject dbUsuario = collection("usuario")
				.find(new BasicDBObject("mail",mail).append("password", password)).one();
		
		if (dbUsuario==null) throw new BussinessException("El usuario y/o password ingresado no existe");
		
		String accessToken = (String) dbUsuario.get("accessToken");
		String refreshToken = (String) dbUsuario.get("refreshToken");
		String id = (String) dbUsuario.get("_id").toString();
		String nombre = (String) dbUsuario.get("nombre").toString();
		
		if (accessToken==null){
			collection("usuario").update(new BasicDBObject("_id",new ObjectId(id)), 
					new BasicDBObject("$set",new BasicDBObject("accessToken",buildAccessToken(nombre))));
		} 
		if (refreshToken==null){
			collection("usuario").update(new BasicDBObject("_id",new ObjectId(id)), 
					new BasicDBObject("$set",new BasicDBObject("refreshToken",buildRefreshToken(nombre))));
		} 
		
		if (accessToken==null || refreshToken==null){
			dbUsuario = collection("usuario")
					.find(new BasicDBObject("mail",mail).append("password", password)).one();
		}
		
		return new CredentialsResponseDTO(dbUsuario);
	}

	public void createUser(SignupDTO signupDTO) throws ParseException{
		String nombre=signupDTO.getNombre();
		collection("usuario").insert(new BasicDBObject("nombre",signupDTO.getNombre())
				.append("apellido", signupDTO.getApellido())
				.append("mail", signupDTO.getMail())
				.append("password", signupDTO.getPassword())
				.append("fnac", signupDTO.getFnac())
				.append("etapaPecs", signupDTO.getEtapaPecs())
				.append("rol", signupDTO.getRol())
				.append("imagenDePerfil",signupDTO.getFoto())
				.append("accessToken", buildAccessToken(nombre))
				.append("refreshToken", buildRefreshToken(nombre))
				.append("foto", signupDTO.getFoto()));
	}
	
	public void isAccessTokenPresent(Token token){
		DBObject dbUsuario = collection("usuario")
				.find(new BasicDBObject("accessToken",token.toString())).one();
		
		if (dbUsuario==null) throw new AuthenticationException("El access token no existe.");
	}
	
	public ObjectId isValid(String userId) {
		final DBCursor cursor = collection("usuario").find(new BasicDBObject("_id",new ObjectId(userId)));
		if (cursor.size()<=0){
			throw new BussinessException("El usuario ingresado no existe.");
		}
		return new ObjectId(userId);
	}

	public String getNewAccessToken(Token token) {
		DBObject dbUsuario = collection("usuario")
				.find(new BasicDBObject("accessToken",token.toString())).one();
		
		if (dbUsuario==null) throw new AuthenticationException("El access token no existe.");
		
		CredentialsResponseDTO cred = new CredentialsResponseDTO(dbUsuario);
		String newToken = buildAccessToken(cred.getNombre());
		collection("usuario").update(new BasicDBObject("_id",new ObjectId(cred.getId())), 
				new BasicDBObject("$set",new BasicDBObject("accessToken",newToken)));
		
		return newToken;
	}

	public void clearTokens(String userId) {
		collection("usuario").update(new BasicDBObject("_id",new ObjectId(userId)), 
				new BasicDBObject("$unset",new BasicDBObject("accessToken",1)));
		
		collection("usuario").update(new BasicDBObject("_id",new ObjectId(userId)), 
				new BasicDBObject("$unset",new BasicDBObject("refreshToken",1)));
		
	}
	
	private String buildAccessToken(String nombre) {
		return new TokenBuilder(nombre).asAT().encode().build().toString();
	}
	
	private String buildRefreshToken(String nombre) {
		return new TokenBuilder(nombre).asRT().encode().build().toString();
	}
	
	
	

}
