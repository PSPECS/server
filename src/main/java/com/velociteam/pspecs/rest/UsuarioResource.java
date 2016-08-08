package com.velociteam.pspecs.rest;

import java.util.List;

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

import com.velociteam.pspecs.dao.UsuariosDao;
import com.velociteam.pspecs.dto.MensajeDTO;
import com.velociteam.pspecs.dto.UsuarioDTO;
import com.velociteam.pspecs.services.FirebaseChatService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuariosDao usuariosDao;
	
	@Autowired 
	private FirebaseChatService chatService;

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
	
	@RequestMapping(value="/mensajes",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMsg(@Valid @RequestBody MensajeDTO mensaje) {
		
        try {
        	chatService.saveMsg(mensaje);
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
	
}
