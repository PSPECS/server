package com.velociteam.pspecs.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.velociteam.pspecs.dto.CredentialsResponseDTO;

@Repository
public class AuthenticationDAO extends AbstractDao{

	@Autowired
	private UsuariosDao usuarioDao;
	
	@Autowired
	public AuthenticationDAO(MongodbDBCreator aCreator) {
		super(aCreator);
	}

	public CredentialsResponseDTO authenticate(String mail, String password) {
		return usuarioDao.getUserInfoByMailYPass(mail, password);
	}

}
