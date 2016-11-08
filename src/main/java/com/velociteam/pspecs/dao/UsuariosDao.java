package com.velociteam.pspecs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.velociteam.pspecs.dto.ContactoDTO;
import com.velociteam.pspecs.dto.CredentialsResponseDTO;
import com.velociteam.pspecs.dto.PassEditionDTO;
import com.velociteam.pspecs.dto.SignupDTO;
import com.velociteam.pspecs.dto.SignupResponseDTO;
import com.velociteam.pspecs.dto.UserEditionDTO;
import com.velociteam.pspecs.dto.UsuarioDTO;
import com.velociteam.pspecs.exception.AuthenticationException;
import com.velociteam.pspecs.exception.BussinessException;
import com.velociteam.pspecs.exception.IncorrectPasswordBussinessException;
import com.velociteam.pspecs.exception.MongoException;
import com.velociteam.pspecs.security.Token;
import com.velociteam.pspecs.security.TokenBuilder;

@Repository
public class UsuariosDao extends AbstractDao{

	@Autowired
	public UsuariosDao(@Qualifier("realDB")MongodbDBCreator aCreator) {
		super(aCreator);
	}

	//CRUD operations
	public void updateToken(String userId, String token) {
		WriteResult wr = updateProperty(userId, "token", token);
		if(wr.getN()<=0) throw new BussinessException("No se actulizo el FB token correctamente");
	}
	
	public String refreshAccessToken(Token token) {
		DBObject dbUsuario = collection("usuario")
				.find(new BasicDBObject("refreshToken",token.toString())).one();
		
		if (dbUsuario==null) throw new AuthenticationException("El refresh token no existe.");
		
		CredentialsResponseDTO cred = new CredentialsResponseDTO(dbUsuario);
		String newAT = buildAccessToken(cred.getNombre());
		updateProperty(cred.getId(), "accessToken", newAT);
		
		return newAT;
	}
	
	//TODO agrega clearTokens a los tests.
	public void clearTokens(String userId) {
		removeToken(userId, "accessToken");
		removeToken(userId, "refreshToken");
		removeToken(userId, "token");
	}
	
	public void createUser(SignupDTO signupDTO){
		collection("usuario").insert(new BasicDBObject("nombre",signupDTO.getNombre())
				.append("apellido", signupDTO.getApellido())
				.append("mail", signupDTO.getMail())
				.append("password", signupDTO.getPassword())
				.append("fnac", signupDTO.getFnac())
				.append("etapaPecs", signupDTO.getEtapaPecs())
				.append("rol", signupDTO.getRol())
				.append("imagenDePerfil",signupDTO.getFoto())
				.append("accessToken", buildAccessToken(signupDTO.getNombre()))
				.append("refreshToken", buildRefreshToken(signupDTO.getNombre())));
	}
	
	public void resetFBToken(String userId, String fbToken) {
		if (fbToken.equalsIgnoreCase(getTokenByUser(userId))) removeToken(userId,"token");
	}
	//Contactos
	public void saveContact(String userId, String userIdAAgregar) {
		UsuarioDTO userOrigen = getUserInfoById(userId);
		if ("usuario regular".equalsIgnoreCase(userOrigen.getRol())){
			collection("usuario").update(new BasicDBObject("_id",new ObjectId(userId)), 
					new BasicDBObject("$push",new BasicDBObject("contactos",buildContact(getUserInfoById(userIdAAgregar)))));
			collection("usuario").update(new BasicDBObject("_id",new ObjectId(userIdAAgregar)), 
					new BasicDBObject("$push",new BasicDBObject("contactos",buildContact(userOrigen))));
		} else {
			throw new BussinessException("Para agregar contactos el rol del usuario debe ser regular");
		}
		
	}

	public void deleteContact(String userId, String userIdAEliminar) {
		UsuarioDTO userOrigen = getUserInfoById(userId);
		if ("usuario regular".equalsIgnoreCase(userOrigen.getRol())){
			collection("usuario").update(new BasicDBObject("_id",new ObjectId(userId)), 
					new BasicDBObject("$pull",new BasicDBObject("contactos",buildContact(getUserInfoById(userIdAEliminar)))));
			collection("usuario").update(new BasicDBObject("_id",new ObjectId(userIdAEliminar)), 
					new BasicDBObject("$pull",new BasicDBObject("contactos",buildContact(userOrigen))));
		} else {
			throw new BussinessException("Para eliminar contactos el rol del usuario debe ser regular");
		}
	}
	
	//Solo usuado para testear.
	public void removeCollection(){
		collection("usuario").remove(new BasicDBObject());
	}
	
	//Queries
	//TODO agregar getContacts a los tests
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
	public String getTokenByUser(String userId) {
		DBObject token = collection("usuario")
				.findOne(new BasicDBObject("_id",new ObjectId(userId)),new BasicDBObject("token",1));
		if(token.get("token")==null) throw new BussinessException("Para el usuario ingresado no existe el FB Token");
		return token.get("token").toString();
	}
	public UsuarioDTO getUserInfoById(String userId) {
		DBObject dbUsuario=null;
		try{
			dbUsuario = collection("usuario")
					.find(new BasicDBObject("_id",new ObjectId(userId))).one();
		} catch (IllegalArgumentException e){
			throw new MongoException("El id ingresado es invalido.",e);
		}
		if (dbUsuario==null) throw new BussinessException("El id ingresado no existe.");
		return new UsuarioDTO(dbUsuario);
	}
	public SignupResponseDTO getUserInfoByNyA(String nombre,String apellido) {
		DBObject dbUsuario = collection("usuario")
				.find(new BasicDBObject("nombre",nombre).append("apellido", apellido)).one();
		if (dbUsuario==null) throw new BussinessException("El nombre o apellido ingresado no existe");
		return new SignupResponseDTO(dbUsuario);
	}
	public CredentialsResponseDTO getUserInfoByEmailAndPass(String mail, String password) {
		DBObject dbUsuario = collection("usuario")
				.find(new BasicDBObject("mail",mail).append("password", password)).one();
		
		if (dbUsuario==null) throw new BussinessException("El usuario y/o password ingresado no existe");
		return new CredentialsResponseDTO(dbUsuario);
	}
	public CredentialsResponseDTO simulateLogin(String userId) {
		DBObject dbUsuario=null;
		try{
			dbUsuario = collection("usuario")
					.find(new BasicDBObject("_id",new ObjectId(userId))).one();
		} catch (IllegalArgumentException e){
			throw new MongoException("El id ingresado es invalido.",e);
		}
		if (dbUsuario==null) throw new BussinessException("El id ingresado no existe.");
		return new CredentialsResponseDTO(dbUsuario);
	}
	
	//Validaciones
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

	//Deberia ser la responsabilidad de otro objeto.
	private String buildAccessToken(String nombre) {
		return new TokenBuilder(nombre).asAT().encode().build().toString();
	}
	private String buildRefreshToken(String nombre) {
		return new TokenBuilder(nombre).asRT().encode().build().toString();
	}
	private BasicDBObject buildContact(UsuarioDTO userInfo) {
		return new BasicDBObject("_id",userInfo.getId())
		.append("nombre", userInfo.getNombre())
		.append("apellido", userInfo.getApellido())
		.append("mail", userInfo.getEmail())
		.append("etapaPecs", userInfo.getEtapaPecs())
		.append("imagenDePerfil", userInfo.getImagenDePerfil())
		.append("rol", userInfo.getRol())
		.append("nuevosMensajes", userInfo.getNuevosMensajes());
	}
	
	//Internal CRUD Operations
	private void removeToken(String userId,String property) {
		WriteResult wr = removeProperty(userId, property);
		if(wr.getN()<=0) throw new BussinessException(String.format("No se elimino el %s correctamente",property));
	}
	public WriteResult updateProperty(String id, String property, String value) {
		return collection("usuario").update(new BasicDBObject("_id",new ObjectId(id)), 
				new BasicDBObject("$set",new BasicDBObject(property,value)));
	}
	private WriteResult removeProperty(String userId, String property) {
		return collection("usuario").update(new BasicDBObject("_id",new ObjectId(userId)), 
				new BasicDBObject("$unset",new BasicDBObject(property,1)));
	}

	public List<ContactoDTO> searchContact(String userId, String search) {
		UsuarioDTO userOrigen = getUserInfoById(userId);
		List<UsuarioDTO> existingContacts = getContacts(userId);
		List<ContactoDTO> contactosSugeridos = new ArrayList<>();
		if ("usuario regular".equalsIgnoreCase(userOrigen.getRol())){
			BasicDBList or = new BasicDBList();
			or.add(new BasicDBObject("rol","Profesional"));
			or.add(new BasicDBObject("rol","Familiar o Amigo"));
			DBCursor dbContactos = collection("usuario").find(new BasicDBObject("apellido",Pattern.compile(search)).append("$or",or));
			for (DBObject usuario : dbContactos) {
				String userIdDB = (String) usuario.get("_id").toString();
				if(!userIdDB.equalsIgnoreCase(userId) && existingContacts.stream().noneMatch(c->c.getId().equalsIgnoreCase(userIdDB))){
					contactosSugeridos.add(new ContactoDTO(usuario));
				}
			}
		}
		return contactosSugeridos.stream().limit(10L).collect(Collectors.toList());
	}

	public void updateUser(String userId, UserEditionDTO userEditionDTO) {
		collection("usuario").update(new BasicDBObject("_id",new ObjectId(userId)),
				new BasicDBObject("$set",new BasicDBObject("nombre",userEditionDTO.getName())
						.append("apellido", userEditionDTO.getLastname())
						.append("mail", userEditionDTO.getEmail())
						.append("etapaPecs", userEditionDTO.getPecsLevel())
						.append("imagenDePerfil", userEditionDTO.getSelectedProfilePic())));
		
		collection("usuario").update(new BasicDBObject("contactos._id",userId),
				new BasicDBObject("$set",new BasicDBObject("contactos.$.nombre",userEditionDTO.getName())
						.append("contactos.$.apellido", userEditionDTO.getLastname())
						.append("contactos.$.mail", userEditionDTO.getEmail())
						.append("contactos.$.etapaPecs", userEditionDTO.getPecsLevel())
						.append("contactos.$.imagenDePerfil", userEditionDTO.getSelectedProfilePic())),false,true);
	}

	public void updatePassword(String userId, PassEditionDTO passDto) {
		if(collection("usuario").find(new BasicDBObject("password",passDto.getOldPassword()).append("_id",new ObjectId(userId))).one()!=null){
			collection("usuario").update(new BasicDBObject("_id",new ObjectId(userId)),
					new BasicDBObject("$set",new BasicDBObject("password",passDto.getPassword())));
		} else{
			throw new IncorrectPasswordBussinessException();
		}
	}

}
