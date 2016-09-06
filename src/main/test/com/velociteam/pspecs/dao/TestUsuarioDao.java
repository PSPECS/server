package com.velociteam.pspecs.dao;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.velociteam.pspecs.dto.SignupDTO;
import com.velociteam.pspecs.dto.TokenDTO;
import com.velociteam.pspecs.dto.UsuarioDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/rest-servlet.xml"})
public class TestUsuarioDao {
	
	@Autowired
	private UsuariosDao usuarioDao;
	
	private SignupDTO martinSignup;
	private String martinId;
	
	@Before
	public void setUp() throws ParseException{
		martinSignup = new SignupDTO("Martin","de la Llave","delallave.martin@gmail.com","1234","10/01/1989","5","Usuario Regular","");
		usuarioDao.createUser(martinSignup);
//		UsuarioDTO martinDto = usuarioDao.getUserInfoByNyA(martinSignup.getNombre(),martinSignup.getApellido());
//		martinId=martinDto.getId();
	}
	
	@Test
	public void testSignupUser() throws ParseException{
		usuarioDao.createUser(martinSignup);
//		UsuarioDTO martinDto = usuarioDao.getUserInfoByNyA(martinSignup.getNombre(),martinSignup.getApellido());
//		Assert.assertEquals("Martin", martinDto.getNombre());
//		Assert.assertEquals("de la Llave", martinDto.getApellido());
	}
	
	@Test
	public void testGetUserInfoById() throws ParseException{
		UsuarioDTO martinDto = usuarioDao.getUserInfoById(martinId);
		Assert.assertEquals(martinId, martinDto.getId());
	}
	
	@Test
	public void testUpdateToken(){
		usuarioDao.updateToken(martinId, new TokenDTO("12345"));
	}
	
	@Test
	public void testGetTokenById(){
		usuarioDao.updateToken(martinId, new TokenDTO("12345"));
		Assert.assertEquals("12345",usuarioDao.getTokenByUser(martinId));
	}

}
