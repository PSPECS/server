package com.velociteam.pspecs.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.velociteam.pspecs.dao.UsuariosDao;
import com.velociteam.pspecs.dto.UsuarioDTO;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuariosDao usuariosDao;

	@RequestMapping(value="/{userId}/contactos",method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createLog(@PathVariable String userId) {
		List<UsuarioDTO> contactos = null;
		
        try {
        	contactos=usuariosDao.getContacts(userId);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getClass(), HttpStatus.OK);
        }

        return new ResponseEntity<>(contactos,HttpStatus.OK);
    }
	
}
