package com.velociteam.pspecs.dao;

import java.net.UnknownHostException;

import org.springframework.stereotype.Repository;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;
import com.velociteam.pspecs.dto.NavigationDTO;

@Repository
public class NavigationDAO {
	
	public void save(NavigationDTO navigationDTO) throws UnknownHostException{
		DB db = new MongoClient(new MongoClientURI("mongodb://admin:uNckDSYqc-FL@127.8.107.130:27017/")).getDB("pspecs");
		
		DBObject dbObject=(DBObject) JSON.parse(JSON.serialize(navigationDTO));
		
		db.getCollection("navegacion").insert(dbObject);
	}

}
