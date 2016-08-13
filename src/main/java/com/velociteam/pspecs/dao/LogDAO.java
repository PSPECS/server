package com.velociteam.pspecs.dao;

import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.velociteam.pspecs.dto.LogDTO;

@Repository
public class LogDAO extends AbstractDao{
	
	public void save(LogDTO logDTO) throws UnknownHostException{
		DB db = getDB();
		DBCollection navegacion = db.getCollection("navegacion");
		
		navegacion.insert(new BasicDBObject("dtInicio", logDTO.getDtInicio())
				.append("dtFin", logDTO.getDtFin())
				.append("usosOk", logDTO.getUsosOk())
				.append("usosNoOk",logDTO.getUsosNoOk())
				.append("userId", validUserId(logDTO.getUserId(),db))
				.append("pictogramas", buildDBPictogramas(logDTO))
				.append("usuariosContactados", buildDBUsuariosContactados(logDTO,db)));
	}

	private ObjectId validUserId(String userId,DB db) {
		final DBCursor cursor = db.getCollection("usuario").find(new BasicDBObject("_id",new ObjectId(userId)));
		if (cursor.size()<=0){
			throw new RuntimeException("El usuario ingresado no existe.");
		}
		return new ObjectId(userId);
	}

	private List<BasicDBObject> buildDBUsuariosContactados(LogDTO logDTO,DB db) {
		return logDTO.getUsuariosContactados().stream().map(u-> new BasicDBObject("userId",validUserId(u.getUserId(),db))
				.append("mensajesEnviados", u.getMensajesEnviados())).collect(Collectors.toList());
	}
	
	private List<BasicDBObject> buildDBPictogramas(LogDTO logDTO) {
		return logDTO.getPictogramas().stream().map(p-> new BasicDBObject("nombre",p.getNombre())
				.append("categoria", p.getCategoria())
				.append("usos", p.getUsos())).collect(Collectors.toList());
	}

}
