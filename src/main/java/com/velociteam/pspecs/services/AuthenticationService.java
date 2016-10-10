package com.velociteam.pspecs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.velociteam.pspecs.dao.UsuariosDao;
import com.velociteam.pspecs.dto.CredentialsResponseDTO;
import com.velociteam.pspecs.security.Token;
import com.velociteam.pspecs.security.TokenBuilder;

@Service
public class AuthenticationService{

	@Autowired
	private UsuariosDao usuarioDao;
	
	public CredentialsResponseDTO authenticate(String mail, String password) {
		return updateAuthTokens(mail, password);
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
	
	public CredentialsResponseDTO updateAuthTokens(String mail, String password) {
		CredentialsResponseDTO userInfo = usuarioDao.getUserInfoByEmailAndPass(mail, password);
		
		if (userInfo.getAccessToken()==null){
			usuarioDao.updateProperty(userInfo.getId(),"accessToken",buildAccessToken(userInfo.getNombre()));
		} 
		if (userInfo.getRefreshToken()==null){
			usuarioDao.updateProperty(userInfo.getId(),"refreshToken",buildRefreshToken(userInfo.getNombre()));
		} 
		//Si se sobreescribieron los tokens.
		if (userInfo.getAccessToken()==null || userInfo.getRefreshToken()==null){
			userInfo=usuarioDao.getUserInfoByEmailAndPass(mail, password);
		}
		
		return userInfo;
	}
	
	//Deberia ser la responsabilidad de otro objeto.
	private String buildAccessToken(String nombre) {
		return new TokenBuilder(nombre).asAT().encode().build().toString();
	}
	
	private String buildRefreshToken(String nombre) {
		return new TokenBuilder(nombre).asRT().encode().build().toString();
	}

}
