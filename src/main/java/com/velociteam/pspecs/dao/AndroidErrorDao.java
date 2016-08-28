package com.velociteam.pspecs.dao;

import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.velociteam.pspecs.dto.AndroidErrorDTO;

@Repository
public class AndroidErrorDao extends AbstractDao{

	public void logError(AndroidErrorDTO error) {
		super.getDB().getCollection("androidLog").insert(
				new BasicDBObject("cause",error.getCause())
				.append("device",new BasicDBObject("brand",error.getDevice().getBrand())
						.append("device", error.getDevice().getDevice())
						.append("id", error.getDevice().getId())
						.append("model", error.getDevice().getModel())
						.append("product", error.getDevice().getProduct()))
				.append("firmware",new BasicDBObject("sdk",error.getFirmware().getSdk())
						.append("release", error.getFirmware().getRelease())
						.append("incremental", error.getFirmware().getIncremental())));
	}

}
