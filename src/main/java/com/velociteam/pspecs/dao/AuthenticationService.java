package com.velociteam.pspecs.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.velociteam.pspecs.dto.CredentialsResponseDTO;
import com.velociteam.pspecs.security.Token;

@Service
public class AuthenticationService{

	@Autowired
	private UsuariosDao usuarioDao;
	
	public CredentialsResponseDTO authenticate(String mail, String password) {
		return usuarioDao.getUserInfoByMailYPass(mail, password);
	}
	
	public void isValidAccessToken(Token token){
		token.isValid();
		usuarioDao.isAccessTokenPresent(token);
	}

	public String isValidRefreshToken(Token token) {
		token.isValid();
		return usuarioDao.getNewAccessToken(token);
	}

	public void logout(String userId) {
		usuarioDao.clearTokens(userId);
	}

}
