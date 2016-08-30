package com.velociteam.pspecs.dao;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.velociteam.pspecs.exception.MongoException;

public abstract class AbstractDao {
	
	private static final String MONGODB_CONN_STRING = "mongodb://admin:uNckDSYqc-FL@127.8.107.130:27017/";
	private static final String PSPECS_DB_NAME = "pspecs";
	private MongoClient mc = null;
	
	protected synchronized DB pspecsDB() {
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
	
	protected DBCollection collection(String name){
		return pspecsDB().getCollection(name);
	}
}
