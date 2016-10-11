package com.velociteam.pspecs.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
		
		emailSender.send(reportRequestDTO.getProfesional(),reportGenerator.generateReport(reportDTO));
	}
	
	private Map<String,List<Tuple>> usuariosContactados(DBCursor navFilt) {
		Map<String,List<Tuple>> usuariosMasContactados = new HashMap<>();
		for (DBObject nav : navFilt) {
			String fecha = fecha(nav);
			
			BasicDBList usuariosContactados = (BasicDBList) nav.get("usuariosContactados");
			for (Object usuarioContactado : usuariosContactados) {
				String userId = ((ObjectId) ((DBObject) usuarioContactado).get("userId")).toHexString();
				Integer msgsEnviados = (Integer) ((DBObject) usuarioContactado).get("mensajesEnviados");
				Tuple newTuple = new Tuple(userId, msgsEnviados);
				updateUserInfo(usuariosMasContactados, fecha, userId, newTuple);
				
			}
			
		}
		
		sortByValue(usuariosMasContactados, 2L);
		
		return usuariosMasContactados;
		
	}
	
	private Map<String,List<Tuple>> pictogramasMasUtilizados(DBCursor navFilt) {
		Map<String,List<Tuple>> pictogramasMasUtilizados = new HashMap<>();
		for (DBObject nav : navFilt) {
			String fecha = fecha(nav);
			
			BasicDBList pictogramas = (BasicDBList) nav.get("pictogramas");
			for (Object pictograma : pictogramas) {
				String nombre = (String) ((DBObject) pictograma).get("nombre");
				Integer usos = (Integer) ((DBObject) pictograma).get("usos");
				Tuple newTuple = new Tuple(nombre, usos);
				updateUserInfo(pictogramasMasUtilizados, fecha, nombre, newTuple);
				
			}
			
		}
		
		sortByValue(pictogramasMasUtilizados, 5L);
		
		return pictogramasMasUtilizados;
		
	}

	private void updateUserInfo(Map<String, List<Tuple>> tableInfo, String fecha, String nombre,
			Tuple newTuple) {
		if (tableInfo.containsKey(fecha)){
			List<Tuple> infoUsuario = tableInfo.get(fecha);
			if (infoUsuario.contains(newTuple)){
				Tuple oldTuple = infoUsuario.get(infoUsuario.indexOf(newTuple));
				if (oldTuple!=null){
					infoUsuario.remove(oldTuple);
					infoUsuario.add(new Tuple(nombre,oldTuple.getValue()+newTuple.getValue()));
				}else {
					//TODO error
				}
			} else {
				infoUsuario.add(newTuple);
			}
			tableInfo.put(fecha, infoUsuario);
		} else {
			List<Tuple> newInfoUsuario = new ArrayList<>();
			newInfoUsuario.add(newTuple);
			tableInfo.put(fecha, newInfoUsuario);
		}
	}

	private String fecha(DBObject nav) {
		Timestamp fInicio = new Timestamp(Long.valueOf((String) nav.get("dtInicio")));
		String fecha = String.valueOf(fInicio.getMonth()+1)+"-"+String.valueOf(1900+fInicio.getYear());
		return fecha;
	}

	private void sortByValue(Map<String, List<Tuple>> pictogramasMasUtilizados, Long maxSize) {
		for (String key : pictogramasMasUtilizados.keySet()) {
			List<Tuple> infoMensual = pictogramasMasUtilizados.get(key);
			infoMensual.sort((left,right)->right.getValue().compareTo(left.getValue()));
			pictogramasMasUtilizados.put(key, infoMensual.stream().limit(maxSize).collect(Collectors.toList()));
		}
	}

	private Map<String,Long> tiempoDeUso(DBCursor navFilt){
		Map<String,Long> tiemposDeUso = new HashMap<>();
		
		for (DBObject navFiltrada : navFilt) {
			Long inicioNav = Long.valueOf((String) navFiltrada.get("dtInicio"));
			Long finNav = Long.valueOf((String) navFiltrada.get("dtFin"));
			String fecha = fecha(navFiltrada);
			Long horas = 0L;
			if (tiemposDeUso.containsKey(fecha)) horas = tiemposDeUso.get(fecha);
			tiemposDeUso.put(fecha,horas+toHours(finNav-inicioNav));
		}
		return tiemposDeUso;
	}
	
	private Long toHours(long l) {
		return l/(1000*60*60);
	}


}
