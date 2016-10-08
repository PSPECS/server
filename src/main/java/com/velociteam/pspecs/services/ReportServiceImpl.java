package com.velociteam.pspecs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.velociteam.pspecs.dto.ReportRequestDTO;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private EmailSender emailSender;
	
	@Override
	public void generateReport(ReportRequestDTO reportRequestDTO) {
		// TODO Obtener Datos.
		// TODO Armar reportes.
		emailSender.send();
	}

}
