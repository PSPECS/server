package com.velociteam.pspecs.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.velociteam.pspecs.dao.NavigationDAO;
import com.velociteam.pspecs.dto.NavigationDTO;

@RestController
@RequestMapping(value = "/navegacion")
public class NavigationResource {
	
	@Autowired
	private NavigationDAO navigationDAO;
	
	@RequestMapping(method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createExchange(@Valid @RequestBody NavigationDTO navigationDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
        	navigationDAO.save(navigationDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

