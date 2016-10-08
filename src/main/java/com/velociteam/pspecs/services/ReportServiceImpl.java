package com.velociteam.pspecs.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.velociteam.pspecs.dao.LogDAO;
import com.velociteam.pspecs.dto.ReportDTO;
import com.velociteam.pspecs.dto.ReportRequestDTO;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private EmailSender emailSender;
	
	@Autowired
	private LogDAO logDao;
	
	@Override
	public void generateReport(ReportRequestDTO reportRequestDTO) {
		// TODO Filtrar Datos contactos y pictogramas.
		ReportDTO reportDTO = new ReportDTO();
		
		DBCursor navFilt = logDao.filteredNavigations(reportRequestDTO.getPaciente(),
				reportRequestDTO.getFechaInicio(), reportRequestDTO.getFechaFin());
		
		reportDTO.setTiemposDeUso(tiempoDeUso(navFilt,reportRequestDTO.getPaciente(),
				reportRequestDTO.getFechaInicio(), reportRequestDTO.getFechaFin()));
		
		// TODO Armar reportes.
		
		emailSender.send();
	}
	
	private Map<String,Long> tiempoDeUso(DBCursor navFilt, String userId,String fInicio,String fFin){
		Map<String,Long> tiemposDeUso = new HashMap<>();
		
		for (DBObject navFiltrada : navFilt) {
			Long inicioNav = (Long) navFiltrada.get("dtInicio");
			Long finNav = (Long) navFiltrada.get("dtFin");
			tiemposDeUso.put(fInicio,toHours(finNav-inicioNav));
		}
		return tiemposDeUso;
	}
	
	private Long toHours(long l) {
		return l/(1000*60*60);
	}


}
