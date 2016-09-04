package com.velociteam.pspecs.dao;

import com.mongodb.DB;
import com.mongodb.DBCollection;

public abstract class AbstractDao {
	
	private MongodbDBCreator aCreator;
	
	public AbstractDao(MongodbDBCreator aCreator){
		this.aCreator=aCreator;
	}
	
	protected DB pspecsDB() {
		return aCreator.getDB();
	}
	
	protected DBCollection collection(String name){
		return pspecsDB().getCollection(name);
	}
}
