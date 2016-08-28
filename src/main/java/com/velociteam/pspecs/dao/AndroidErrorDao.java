package com.velociteam.pspecs.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.velociteam.pspecs.dto.AndroidErrorDTO;
import com.velociteam.pspecs.dto.DeviceDTO;
import com.velociteam.pspecs.dto.FirmwareDTO;

@Repository
public class AndroidErrorDao extends AbstractDao{

	public void logError(AndroidErrorDTO error) {
		super.getDB().getCollection("androidLog").insert(
				new BasicDBObject("cause",error.getCause())
				.append("device",buildDevice(error.getDevice()))
				.append("firmware",buildFirmware(error.getFirmware())));
		
	}

	private List<BasicDBObject> buildFirmware(List<FirmwareDTO> firmware) {
		return firmware.stream().filter(item->item!=null).map(item-> new BasicDBObject("sdk",item.getSdk())
				.append("release", item.getRelease())
				.append("incremental", item.getIncremental()))
				.collect(Collectors.toList());
	}

	private List<BasicDBObject> buildDevice(List<DeviceDTO> device) {
		return device.stream().filter(item->item!=null).map(item-> new BasicDBObject("brand",item.getBrand())
				.append("device", item.getDevice())
				.append("model", item.getModel())
				.append("id", item.getId())
				.append("product", item.getProduct()))
				.collect(Collectors.toList());
	}

}
