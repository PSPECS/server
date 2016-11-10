package com.velociteam.pspecs.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBList;
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
	private ReportGenerator reportGenerator;
	
	@Autowired
	private LogDAO logDao;
	
	@Override
	public void generateReport(ReportRequestDTO reportRequestDTO) {
		DBCursor navFilt = logDao.filteredNavigations(reportRequestDTO.getPaciente(),
				reportRequestDTO.getFechaInicio(), reportRequestDTO.getFechaFin());

		final ReportDTO reportDTO = new ReportDTO(
				tiempoDeUso(navFilt),
				usuariosContactados(navFilt),
				pictogramasMasUtilizados(navFilt));
		
		emailSender.send(reportRequestDTO.getProfesional(),reportGenerator.generateReport(reportRequestDTO.paciente,reportDTO));
	}
	
	private List<Tuple> usuariosContactados(DBCursor navFilt) {
		List<Tuple> usuariosMasContactados = new ArrayList<>();
		for (DBObject nav : navFilt) {
			BasicDBList usuariosContactados = (BasicDBList) nav.get("usuariosContactados");
			for (Object usuarioContactado : usuariosContactados) {
				String userId = ((ObjectId) ((DBObject) usuarioContactado).get("userId")).toHexString();
				Integer msgsEnviados = (Integer) ((DBObject) usuarioContactado).get("mensajesEnviados");
				Tuple newTuple = new Tuple(userId, msgsEnviados);
				updateUserInfo(usuariosMasContactados, userId, newTuple);
			}
		}
		return usuariosMasContactados;
	}
	
	private List<Tuple> pictogramasMasUtilizados(DBCursor navFilt) {
		List<Tuple> pictogramasMasUtilizados = new ArrayList<>();
		for (DBObject nav : navFilt) {
			BasicDBList pictogramas = (BasicDBList) nav.get("pictogramas");
			for (Object pictograma : pictogramas) {
				String nombre = (String) ((DBObject) pictograma).get("nombre");
				Integer usos = (Integer) ((DBObject) pictograma).get("usos");
				Tuple newTuple = new Tuple(nombre, usos);
				updateUserInfo(pictogramasMasUtilizados, nombre, newTuple);
			}
		}
		return pictogramasMasUtilizados;
		
	}

	private void updateUserInfo(List<Tuple> tableInfo, String nombre,Tuple newTuple) {
		if (tableInfo.contains(newTuple)){
			Tuple oldTuple = tableInfo.get(tableInfo.indexOf(newTuple));
			if (oldTuple!=null){
				tableInfo.remove(oldTuple);
				tableInfo.add(new Tuple(nombre,oldTuple.getValue()+newTuple.getValue()));
			}else {
				//TODO error
			}
		} else {
			tableInfo.add(newTuple);
		}
	}

	private String fecha(DBObject nav) {
		Timestamp fInicio = new Timestamp(Long.valueOf((String) nav.get("dtInicio")));
		String fecha = String.valueOf(fInicio.getMonth()+1)+"-"+String.valueOf(1900+fInicio.getYear());
		return fecha;
	}

	private Map<String,Double> tiempoDeUso(DBCursor navFilt){
		Map<String,Double> tiemposDeUso = new HashMap<>();
		
		for (DBObject navFiltrada : navFilt) {
			Long inicioNav = Long.valueOf((String) navFiltrada.get("dtInicio"));
			Long finNav = Long.valueOf((String) navFiltrada.get("dtFin"));
			String fecha = fecha(navFiltrada);
			double horas = 0;
			if (tiemposDeUso.containsKey(fecha)) horas = tiemposDeUso.get(fecha);
			tiemposDeUso.put(fecha,horas+toHours(finNav-inicioNav));
		}
		return tiemposDeUso;
	}
	
	private double toHours(long l) {
		return (double) l/(1000*60*60);
	}


}
