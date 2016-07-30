package com.velociteam.pspecs.dao;

import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.velociteam.pspecs.dto.LogDTO;

@Repository
public class LogDAO {
	
	public void save(LogDTO logDTO) throws UnknownHostException{
		DB db = new MongoClient(new MongoClientURI("mongodb://admin:uNckDSYqc-FL@127.8.107.130:27017/")).getDB("pspecs");
		
		db.getCollection("navegacion").insert(new BasicDBObject("dtInicio", logDTO.getDtInicio())
				.append("dtFin", logDTO.getDtFin())
				.append("usosOk", logDTO.getUsosOk())
				.append("usosNoOk",logDTO.getUsosNoOk())
				.append("usuario", logDTO.getUsuario())
				.append("pictogramas", buildDBPictogramas(logDTO))
				.append("usuariosConectados", buildDBUsuariosConectados(logDTO)));
	}

	private List<BasicDBObject> buildDBUsuariosConectados(LogDTO logDTO) {
		return logDTO.getUsuariosConectados().stream().map(p-> new BasicDBObject("usuario",p.getUsuario())
				.append("mensajesEnviados", p.getMensajesEnviados())).collect(Collectors.toList());
	}

	private List<BasicDBObject> buildDBPictogramas(LogDTO logDTO) {
		return logDTO.getPictogramas().stream().map(p-> new BasicDBObject("nombre",p.getNombre())
				.append("categoria", p.getCategoria())
				.append("usos", p.getUsos())).collect(Collectors.toList());
	}

}
