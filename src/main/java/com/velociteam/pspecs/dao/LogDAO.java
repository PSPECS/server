package com.velociteam.pspecs.dao;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.velociteam.pspecs.dto.LogDTO;
import com.velociteam.pspecs.exception.BussinessException;

@Repository
public class LogDAO extends AbstractDao{
	
	@Autowired
	public LogDAO(MongodbDBCreator aCreator) {
		super(aCreator);
	}

	@Autowired
	private UsuariosDao usDao;
	
	public void save(LogDTO logDTO) throws UnknownHostException{
		DBCollection navegacion = collection("navegacion");
		
		navegacion.insert(new BasicDBObject("dtInicio", logDTO.getDtInicio())
				.append("dtFin", logDTO.getDtFin())
				.append("usosOk", logDTO.getUsosOk())
				.append("usosNoOk",logDTO.getUsosNoOk())
				.append("userId", usDao.isValid(logDTO.getUserId()))
				.append("pictogramas", buildDBPictogramas(logDTO))
				.append("usuariosContactados", buildDBUsuariosContactados(logDTO)));
	}

	
	public DBCursor filteredNavigations(String userId, String fInicio, String fFin) {
		DBCollection navegacion = collection("navegacion");
		Long fInicioTS;
		Long fFinTS;
		try {
			fInicioTS = new SimpleDateFormat("dd/MM/yyyy-hh:mm").parse(fInicio).getTime();
			fFinTS = new SimpleDateFormat("dd/MM/yyyy-hh:mm").parse(fFin).getTime();
		} catch (ParseException e) {
			throw new BussinessException("El formato de la fecha ingresada es erroneo.",e);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -3);
		
		if (fInicioTS>calendar.getTimeInMillis()){
			throw new BussinessException("El reporte debe ser generado con una fecha de inicio mayor a 3 meses desde la fecha.");
		} 
		if ((fFinTS-fInicioTS)>threeMonths()){
			throw new BussinessException("El reporte debe ser generado con un periodo mayor a tres meses.");
		}
		
		DBCursor navegacionesFiltradas = navegacion.find(new BasicDBObject("userId",new ObjectId(userId))
				.append("dtInicio", new BasicDBObject("$gte",fInicioTS))
				.append("dtFin", new BasicDBObject("$lte",fFinTS)));
		return navegacionesFiltradas;
	}

	private long threeMonths() {
		return 1000*60*60*24*90;
	}


	private List<BasicDBObject> buildDBUsuariosContactados(LogDTO logDTO) {
		return logDTO.getUsuariosContactados().stream().map(u-> new BasicDBObject("userId",usDao.isValid(u.getUserId()))
				.append("mensajesEnviados", u.getMensajesEnviados())).collect(Collectors.toList());
	}
	
	private List<BasicDBObject> buildDBPictogramas(LogDTO logDTO) {
		return logDTO.getPictogramas().stream().map(p-> new BasicDBObject("nombre",p.getNombre())
				.append("usos", p.getUsos())).collect(Collectors.toList());
	}

}
