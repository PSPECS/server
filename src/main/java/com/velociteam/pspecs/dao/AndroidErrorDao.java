package com.velociteam.pspecs.dao;

import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.velociteam.pspecs.dto.AndroidErrorDTO;

@Repository
public class AndroidErrorDao extends AbstractDao{

	public void logError(AndroidErrorDTO error) {
		super.getDB().getCollection("androidLog").insert(
				new BasicDBObject("cause",error.getCause())
				.append("device",error.getDevice())
				.append("firmware",error.getFirmware()));
	}

}
