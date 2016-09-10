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

import com.velociteam.pspecs.dao.AndroidErrorDao;
import com.velociteam.pspecs.dto.AndroidErrorDTO;
import com.velociteam.pspecs.exception.AuthenticationException;

@RestController
@RequestMapping(value = "/errorlog")
public class AndroidErrorResource extends AbstractResource {
	
	@Autowired
	private AndroidErrorDao androidErrorDao;
	
	@RequestMapping(method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMsg(@RequestHeader("Authorization") String autHeader,@Valid @RequestBody AndroidErrorDTO error) {
		
        try {
        	auth(autHeader);
        	androidErrorDao.logError(error);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(e,HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
