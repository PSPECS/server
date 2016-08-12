package com.velociteam.pspecs.dao;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public abstract class AbstractDao {
	
	protected DB getDB() {
		DB db = null;
		try {
			db = new MongoClient(new MongoClientURI("mongodb://admin:uNckDSYqc-FL@127.8.107.130:27017/")).getDB("pspecs");
		} catch (UnknownHostException e) {
			throw new RuntimeException(e.getCause());
		}
		return db;
	}

}
