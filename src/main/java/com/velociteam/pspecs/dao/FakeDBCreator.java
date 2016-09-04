package com.velociteam.pspecs.dao;

import org.springframework.stereotype.Component;

import com.github.fakemongo.Fongo;
import com.mongodb.DB;

//Solo usado para unit testing.
//@Component("fakeDB")
public class FakeDBCreator implements MongodbDBCreator {
	private Fongo server;
	
	public FakeDBCreator() {}

	@Override
	public DB getDB() {
		if (server!=null){
			return server.getDB(PSPECS_DB_NAME);
		}
		server=new Fongo("Server");
		return server.getDB(PSPECS_DB_NAME);
	}

}
