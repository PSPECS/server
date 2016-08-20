package com.velociteam.pspecs.dao;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public abstract class AbstractDao {
	
	private MongoClient mc = null;
	
	protected synchronized DB getDB() {
		DB db = null;
		if (mc!=null){
			return mc.getDB("pspecs");
		} else{
			try {
				mc = new MongoClient(new MongoClientURI("mongodb://admin:uNckDSYqc-FL@127.8.107.130:27017/"));
				db = mc.getDB("pspecs");
			} catch (UnknownHostException e) {
				throw new RuntimeException(e.getCause());
			}
			return db;
		}
		
	}
}
