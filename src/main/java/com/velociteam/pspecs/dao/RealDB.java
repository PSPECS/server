package com.velociteam.pspecs.dao;

import java.net.UnknownHostException;

import org.springframework.stereotype.Component;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.velociteam.pspecs.exception.MongoException;

@Component
public class RealDB implements MongodbDBCreator {

	public RealDB() {}

	private static final String MONGODB_CONN_STRING = "mongodb://admin:uNckDSYqc-FL@127.8.107.130:27017/";
	private MongoClient mc = null;
	
	@Override
	public synchronized DB getDB() {
		DB db = null;
		if (mc!=null){
			return mc.getDB(PSPECS_DB_NAME);
		} else{
			try {
				mc = new MongoClient(new MongoClientURI(MONGODB_CONN_STRING));
				db = mc.getDB(PSPECS_DB_NAME);
			} catch (UnknownHostException e) {
				throw new MongoException("IP address of a host could not be determined.",e.getCause());
			}
			return db;
		}
	}

}
