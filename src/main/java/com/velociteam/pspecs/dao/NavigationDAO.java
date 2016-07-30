package com.velociteam.pspecs.dao;

import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.velociteam.pspecs.dto.NavigationDTO;

@Repository
public class NavigationDAO {
	
	public void save(NavigationDTO navigationDTO) throws UnknownHostException{
		DB db = new MongoClient(new MongoClientURI("mongodb://admin:uNckDSYqc-FL@127.8.107.130:27017/")).getDB("pspecs");
		
		db.getCollection("navegacion").insert(new BasicDBObject("dtInicio", "")
				.append("dtFin", navigationDTO.getDtFin())
				.append("usosOk", navigationDTO.getUsosOk())
				.append("usosNoOk",navigationDTO.getUsosNoOk())
				.append("usuario", navigationDTO.getUsuario())
				.append("pictogramas", buildDBPictogramas(navigationDTO)));
	}

	private List<BasicDBObject> buildDBPictogramas(NavigationDTO navigationDTO) {
		return navigationDTO.getPictogramas().stream().map(p-> new BasicDBObject("nombre",p.getNombre())
				.append("categoria", p.getCategoria())
				.append("usos", p.getUsos())).collect(Collectors.toList());
	}

}
