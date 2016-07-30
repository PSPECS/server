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
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.velociteam.pspecs.dto.LogDTO;

@Repository
public class LogDAO {
	
	public void save(LogDTO logDTO) throws UnknownHostException{
		DB db = new MongoClient(new MongoClientURI("mongodb://admin:uNckDSYqc-FL@127.8.107.130:27017/")).getDB("pspecs");
		DBCollection navegacion = db.getCollection("navegacion");
		
		navegacion.insert(new BasicDBObject("dtInicio", logDTO.getDtInicio())
				.append("dtFin", logDTO.getDtFin())
				.append("usosOk", logDTO.getUsosOk())
				.append("usosNoOk",logDTO.getUsosNoOk())
				.append("userId", validUserId(logDTO.getUserId(),db))
				.append("usuario", logDTO.getUsuario())
				.append("pictogramas", buildDBPictogramas(logDTO))
				.append("usuariosContactados", buildDBUsuariosContactados(logDTO,db)));
	}

	private ObjectId validUserId(Long userId,DB db) {
		final DBCursor cursor = db.getCollection("usuario").find(new BasicDBObject("_id",String.valueOf(userId)));
		if (cursor.size()<=0){
			throw new RuntimeException("El usuario ingresado no existe.");
		}
		return new ObjectId(String.valueOf(userId));
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
