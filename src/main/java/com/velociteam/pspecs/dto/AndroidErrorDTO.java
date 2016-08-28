package com.velociteam.pspecs.dto;

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
	private DeviceDTO device;
	private FirmwareDTO firmware;
	public AndroidErrorDTO() {}
	public AndroidErrorDTO(String cause, DeviceDTO device, FirmwareDTO firmware) {
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
	public DeviceDTO getDevice() {
		return device;
	}
	public void setDevice(DeviceDTO device) {
		this.device = device;
	}
	public FirmwareDTO getFirmware() {
		return firmware;
	}
	public void setFirmware(FirmwareDTO firmware) {
		this.firmware = firmware;
	}
}
