package com.velociteam.pspecs.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.velociteam.pspecs.dao.LogDAO;
import com.velociteam.pspecs.dto.LogDTO;
import com.velociteam.pspecs.exception.AuthenticationException;

@RestController
@RequestMapping(value = "/log")
public class NavigationResource extends AbstractResource{
	
	@Autowired
	private LogDAO logDAO;
	
	@RequestMapping(method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createLog(@RequestHeader("Authorization") String autHeader,@Valid @RequestBody LogDTO logDTO) {

        try {
        	auth(autHeader);
        	logDAO.save(logDTO);
        } catch(AuthenticationException e){
        	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

