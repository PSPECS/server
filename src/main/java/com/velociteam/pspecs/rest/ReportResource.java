package com.velociteam.pspecs.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.velociteam.pspecs.dto.ReportRequestDTO;
import com.velociteam.pspecs.exception.AuthenticationException;
import com.velociteam.pspecs.services.ReportService;

@RestController
@RequestMapping(value = "/reportes")
public class ReportResource extends AbstractResource{
	@Autowired 
	private ReportService reportService;
	
	@RequestMapping(method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signup(@RequestHeader("Authorization") String autHeader,@RequestBody ReportRequestDTO reportRequestDTO) {
		try {
        	auth(autHeader);
        	reportService.generateReport(reportRequestDTO);
        } catch(AuthenticationException e){
        	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
