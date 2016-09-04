package com.velociteam.pspecs.dao;

import com.mongodb.DB;

public interface MongodbDBCreator {
	
	public static final String PSPECS_DB_NAME = "pspecs";
	
	public abstract DB getDB();

}
