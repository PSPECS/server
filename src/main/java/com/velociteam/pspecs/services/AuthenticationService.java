package com.velociteam.pspecs.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.velociteam.pspecs.dao.UsuariosDao;
import com.velociteam.pspecs.dto.CredentialsResponseDTO;
import com.velociteam.pspecs.dto.UsuarioDTO;
import com.velociteam.pspecs.exception.AuthenticationException;
import com.velociteam.pspecs.exception.BussinessException;
import com.velociteam.pspecs.security.Token;
import com.velociteam.pspecs.security.TokenBuilder;

@Service
public class AuthenticationService{

	@Autowired
	private UsuariosDao usuarioDao;
	
	public CredentialsResponseDTO authenticate(String mail, String password) {
		CredentialsResponseDTO userInfo = usuarioDao.getUserInfoByEmailAndPass(mail, password);
		
		try{
			new Token(userInfo.getRefreshToken(),false).isValid();
		} catch(AuthenticationException e){
			usuarioDao.updateProperty(userInfo.getId(),"refreshToken",buildRefreshToken(userInfo.getNombre()));
		}
		return usuarioDao.getUserInfoByEmailAndPass(mail, password);
	}
	
	public void isValidAccessToken(Token token){
		token.isValid();
		usuarioDao.isAccessTokenPresent(token);
	}

	public String isValidRefreshToken(Token token) {
		token.isValid();
		return usuarioDao.refreshAccessToken(token);
	}

	public void logout(String userId) {
		usuarioDao.clearTokens(userId);
	}

	public CredentialsResponseDTO simulateAuthentication(String userId, String patientUserId) {
		List<UsuarioDTO> users = usuarioDao.getContacts(userId);
		
		if(users.stream().anyMatch(user->patientUserId.equalsIgnoreCase(user.getId()))){
			return usuarioDao.simulateLogin(patientUserId);
		}
		throw new BussinessException("El usuario ingresado no puede realizar esta operación");
	}
	
	//Deberia ser la responsabilidad de otro objeto.
	private String buildRefreshToken(String nombre) {
		return new TokenBuilder(nombre).asRT().encode().build().toString();
	}

}
