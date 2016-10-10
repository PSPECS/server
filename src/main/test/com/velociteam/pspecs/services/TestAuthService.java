package com.velociteam.pspecs.services;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.velociteam.pspecs.dao.UsuariosDao;
import com.velociteam.pspecs.dto.SignupDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/rest-servlet.xml"})
public class TestAuthService {
	
	@Autowired
	private UsuariosDao usuarioDao;
	@Autowired
	private AuthenticationService authService;
	
	private SignupDTO martinSignup;
	
	@Before
	public void setUp() throws ParseException{
		martinSignup = new SignupDTO("Martin","de la Llave","delallave.martin@gmail.com","1234","10/01/1989","5","Usuario Regular","");
		usuarioDao.createUser(martinSignup);
	}
	
	@Test
	public void shouldGetUserInfoByEmailAndPass(){
		Assert.assertNotNull(authService.updateAuthTokens(martinSignup.getMail(),martinSignup.getPassword()));
	}

}
