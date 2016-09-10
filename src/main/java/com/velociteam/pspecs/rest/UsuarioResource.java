package com.velociteam.pspecs.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.velociteam.pspecs.dao.MensajesDao;
import com.velociteam.pspecs.dao.UsuariosDao;
import com.velociteam.pspecs.dto.MensajeDTO;
import com.velociteam.pspecs.dto.RequestMsgDTO;
import com.velociteam.pspecs.dto.TokenDTO;
import com.velociteam.pspecs.dto.UsuarioDTO;
import com.velociteam.pspecs.exception.AuthenticationException;
import com.velociteam.pspecs.services.FirebaseChatService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource extends AbstractResource {
	
	@Autowired
	private UsuariosDao usuariosDao;
	
	@Autowired
	private MensajesDao mensajesDao;

	@Autowired 
	private FirebaseChatService chatService;
	
	@RequestMapping(value="/{userId}/newRefreshToken",method = RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateToken(@RequestHeader("Authorization") String autHeader, @PathVariable String userId, @Valid @RequestBody TokenDTO tokenDTO) {
		
        try {
        	auth(autHeader);
        	usuariosDao.updateToken(userId,tokenDTO);
        } catch (AuthenticationException e){
        	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
	
	@RequestMapping(value="/{userId}/contactos",method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getContacts(@RequestHeader("Authorization") String autHeader,@PathVariable String userId) {
		List<UsuarioDTO> contactos = null;
		
        try {
        	auth(autHeader);
        	contactos=usuariosDao.getContacts(userId);
        } catch (AuthenticationException e){
        	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(contactos,HttpStatus.OK);
    }
	
	@RequestMapping(value="/{userId}/enviarMensajes",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMsg(@RequestHeader("Authorization") String autHeader,@PathVariable String userId,@Valid @RequestBody MensajeDTO mensaje) {
		
        try {
        	auth(autHeader);
        	chatService.saveMsg(userId,mensaje);
        } catch (AuthenticationException e){
        	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
	
	@RequestMapping(value="/{userId}/mensajes",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMsgs(@RequestHeader("Authorization") String autHeader,@PathVariable String userId,@Valid @RequestBody RequestMsgDTO requestMsg) {
		Map<String,Object> mensajes = new HashMap<>();
		
        try {
        	auth(autHeader);
        	mensajes.put("usuario", usuariosDao.getUserInfoById(requestMsg.getUsuarioAChatear()));
        	mensajes.put("mensajes", mensajesDao.search(userId,requestMsg));
        } catch (AuthenticationException e){
        	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(mensajes,HttpStatus.OK);
    }
	
}
