package com.velociteam.pspecs.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.velociteam.pspecs.dao.UsuariosDao;
import com.velociteam.pspecs.dto.CredentialsDTO;
import com.velociteam.pspecs.dto.CredentialsResponseDTO;
import com.velociteam.pspecs.dto.SignupDTO;
import com.velociteam.pspecs.dto.SignupResponseDTO;
import com.velociteam.pspecs.dto.TokenDTO;
import com.velociteam.pspecs.exception.AuthenticationException;
import com.velociteam.pspecs.exception.BussinessException;
import com.velociteam.pspecs.security.Token;
import com.velociteam.pspecs.services.AuthenticationService;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationResource extends AbstractResource {

	@Autowired
	private UsuariosDao usuarioDao;
	
	@Autowired
	private AuthenticationService authService;
	
	@RequestMapping(value="/signup",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signup(@RequestBody SignupDTO signupDTO) {
		SignupResponseDTO responseDTO = null;
        try {
        	usuarioDao.createUser(signupDTO);
        	responseDTO = usuarioDao.getUserInfoByNyA(signupDTO.getNombre(), signupDTO.getApellido());
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);
    }
	
	@RequestMapping(value="/login",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> auth(@RequestBody CredentialsDTO credentialsDTO) {
		CredentialsResponseDTO responseDTO = null;
        try {
        	responseDTO = authService.authenticate(credentialsDTO.getMail(),credentialsDTO.getPassword());
        } catch(BussinessException e){
        	return new ResponseEntity<> (e,HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }
	
	@RequestMapping(value="/{userId}/simulationAccessToken/{patientUserId}",method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> simulatedAuth(@RequestHeader("Authorization") String autHeader,@PathVariable String userId,@PathVariable String patientUserId) {
		CredentialsResponseDTO responseDTO = null;
        try {
        	responseDTO = authService.simulateAuthentication(userId,patientUserId);
        } catch(BussinessException e){
        	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }
	
	@RequestMapping(value="/{userId}/logout",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String autHeader,@PathVariable String userId) {
        try {
        	auth(autHeader);
        	authService.logout(userId);
        } catch(AuthenticationException e){
        	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@RequestMapping(value="/newToken",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> newToken(@Valid @RequestBody TokenDTO refreshToken) {
		String newToken = null;
        try {
        	newToken = authService.isValidRefreshToken(new Token(refreshToken.getRefreshToken(),false));
        } catch (AuthenticationException e){
        	return new ResponseEntity<>(e,HttpStatus.UNAUTHORIZED);
        }catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(newToken,HttpStatus.CREATED);
    }
	
}
