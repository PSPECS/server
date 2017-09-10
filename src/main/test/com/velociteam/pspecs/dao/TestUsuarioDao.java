package com.velociteam.pspecs.dao;

import java.text.ParseException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.velociteam.pspecs.dto.SignupDTO;
import com.velociteam.pspecs.dto.SignupResponseDTO;
import com.velociteam.pspecs.dto.TokenDTO;
import com.velociteam.pspecs.exception.BussinessException;
import com.velociteam.pspecs.exception.MongoException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/rest-servlet.xml"})
public class TestUsuarioDao {
	
	private static final String LASTNAME_NOT_PRESENT = "apellido";
	private static final String NAME_NOT_PRESENT = "nombre";
	private static final String EMAIL_NOT_PRESENT = "emailInexistente";
	private static final String INVALID_USER_ID = "123123";

	@Autowired
	private UsuariosDao usuarioDao;
	
	private SignupDTO martinSignup;
	
	@Before
	public void setUp() throws ParseException{
		martinSignup = new SignupDTO("Martin","de la Llave","delallave.martin@gmail.com","1234","5","Usuario Regular","");
	}
	
	@After
	public void tearDown(){
		usuarioDao.removeCollection();
	}
	
	@Test
	public void shouldSignupSuccesfully(){
		usuarioDao.createUser(martinSignup);
	}
	
	@Test
	public void shouldGetUserInfoByNameAndLastName(){
		usuarioDao.createUser(martinSignup);
		Assert.assertNotNull(usuarioDao.getUserInfoByNyA(martinSignup.getNombre(),martinSignup.getApellido()));
	}
	
	@Test(expected=BussinessException.class)
	public void shouldGetUserInfoByNameAndLastNameWithError(){
		usuarioDao.createUser(martinSignup);
		Assert.assertNotNull(usuarioDao.getUserInfoByNyA(NAME_NOT_PRESENT,LASTNAME_NOT_PRESENT));
	}

	@Test
	public void shouldGetUserInfoById(){
		usuarioDao.createUser(martinSignup);
		SignupResponseDTO martinDtoByNyA = usuarioDao.getUserInfoByNyA(martinSignup.getNombre(),martinSignup.getApellido());
		Assert.assertNotNull(usuarioDao.getUserInfoById(martinDtoByNyA.getId()));
	}
	
	@Test(expected=MongoException.class)
	public void shouldGetUserInfoByIdWithError(){
		usuarioDao.createUser(martinSignup);
		Assert.assertNotNull(usuarioDao.getUserInfoById(INVALID_USER_ID));
	}
	
	@Test
	public void shouldGetUserInfoByEmailAndPass(){
		usuarioDao.createUser(martinSignup);
		Assert.assertNotNull(usuarioDao.getUserInfoByEmailAndPass(martinSignup.getMail(),martinSignup.getPassword()));
	}
	
	@Test(expected=BussinessException.class)
	public void shouldGetUserInfoByEmailAndPassWithError(){
		usuarioDao.createUser(martinSignup);
		Assert.assertNotNull(usuarioDao.getUserInfoByEmailAndPass(EMAIL_NOT_PRESENT,martinSignup.getPassword()));
	}
	
	@Test
	public void testUpdateToken(){
		usuarioDao.createUser(martinSignup);
		SignupResponseDTO martinDtoByNyA = usuarioDao.getUserInfoByNyA(martinSignup.getNombre(),martinSignup.getApellido());
		usuarioDao.updateToken(martinDtoByNyA.getId(), new TokenDTO("12345").getRefreshToken());
	}
	
	@Test
	public void shouldGetTokenByUserId(){
		usuarioDao.createUser(martinSignup);
		SignupResponseDTO martinDtoByNyA = usuarioDao.getUserInfoByNyA(martinSignup.getNombre(),martinSignup.getApellido());
		usuarioDao.updateToken(martinDtoByNyA.getId(), new TokenDTO("12345").getRefreshToken());
		Assert.assertEquals("12345",usuarioDao.getTokenByUser(martinDtoByNyA.getId()));
	}
	
	@Test(expected=BussinessException.class)
	public void shouldGetTokenByUserIdWithErrorBecauseDoesntExists(){
		usuarioDao.createUser(martinSignup);
		SignupResponseDTO martinDtoByNyA = usuarioDao.getUserInfoByNyA(martinSignup.getNombre(),martinSignup.getApellido());
		Assert.assertEquals("12345",usuarioDao.getTokenByUser(martinDtoByNyA.getId()));
	}
	
	@Test
	public void shouldResetFBToken(){
		usuarioDao.createUser(martinSignup);
		SignupResponseDTO martinDtoByNyA = usuarioDao.getUserInfoByNyA(martinSignup.getNombre(),martinSignup.getApellido());
		usuarioDao.updateToken(martinDtoByNyA.getId(), new TokenDTO("1234").getRefreshToken());
		usuarioDao.resetFBToken(martinDtoByNyA.getId(),"12345");
	}
	
}
