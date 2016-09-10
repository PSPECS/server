package com.velociteam.pspecs.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.velociteam.pspecs.dto.CredentialsResponseDTO;

@Service
public class AuthenticationService{

	@Autowired
	private UsuariosDao usuarioDao;
	
	public CredentialsResponseDTO authenticate(String mail, String password) {
		return usuarioDao.getUserInfoByMailYPass(mail, password);
	}
	
	public void isValidToken(String token){
		usuarioDao.validateAccessToken(token);
	}

	public String refreshToken(String refreshToken) {
		return usuarioDao.getNewAccessToken(refreshToken);
	}

	public void logout(String userId) {
		usuarioDao.clearTokens(userId);
	}

}
