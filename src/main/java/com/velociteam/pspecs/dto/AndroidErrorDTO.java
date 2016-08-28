package com.velociteam.pspecs.dto;

import java.util.List;

public class AndroidErrorDTO {
//	  {
//	        cause: "stackTrace",
//	        device: {
//	            brand: "brand"
//	            device: "device"
//	            model: "model"
//	            id: "id"
//	            product: "product"
//	        }
//	        firmware: {
//	            skd: "skd"
//	            release: "release"
//	            incremental: "incremental"
//	        }
	
	private String cause;
	private List<DeviceDTO> device;
	private List<FirmwareDTO> firmware;
	public AndroidErrorDTO() {}
	public AndroidErrorDTO(String cause, List<DeviceDTO> device, List<FirmwareDTO> firmware) {
		super();
		this.cause = cause;
		this.device = device;
		this.firmware = firmware;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public List<DeviceDTO> getDevice() {
		return device;
	}
	public void setDevice(List<DeviceDTO> device) {
		this.device = device;
	}
	public List<FirmwareDTO> getFirmware() {
		return firmware;
	}
	public void setFirmware(List<FirmwareDTO> firmware) {
		this.firmware = firmware;
	}
}
