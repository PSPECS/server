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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.velociteam.pspecs.dao.MensajesDao;
import com.velociteam.pspecs.dao.UsuariosDao;
import com.velociteam.pspecs.dto.MensajeDTO;
import com.velociteam.pspecs.dto.RequestMsgDTO;
import com.velociteam.pspecs.dto.TokenDTO;
import com.velociteam.pspecs.dto.UsuarioDTO;
import com.velociteam.pspecs.services.FirebaseChatService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuariosDao usuariosDao;
	
	@Autowired
	private MensajesDao mensajesDao;
	
	@Autowired 
	private FirebaseChatService chatService;

	@RequestMapping(value="/{userId}/newRefreshToken",method = RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateToken(@PathVariable String userId, @Valid @RequestBody TokenDTO tokenDTO) {
		
        try {
        	usuariosDao.updateToken(userId,tokenDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
	
	@RequestMapping(value="/{userId}/contactos",method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createLog(@PathVariable String userId) {
		List<UsuarioDTO> contactos = null;
		
        try {
        	contactos=usuariosDao.getContacts(userId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(contactos,HttpStatus.OK);
    }
	
	@RequestMapping(value="/{userId}/mensajes",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMsg(@PathVariable String userId,@RequestBody MensajeDTO mensaje) {
		
        try {
        	chatService.saveMsg(userId,mensaje);
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
	
	@RequestMapping(value="/{userId}/mensajes",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMsgs(@PathVariable String userId,@RequestBody RequestMsgDTO requestMsg) {
		Map<String,Object> mensajes = new HashMap<>();
		
        try {
        	mensajes.put("usuario", usuariosDao.getUserInfoById(userId));
        	mensajes.put("mensajes", mensajesDao.search(userId,requestMsg));
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(mensajes,HttpStatus.OK);
    }
	
}
