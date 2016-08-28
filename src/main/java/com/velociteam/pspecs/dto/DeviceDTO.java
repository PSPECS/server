package com.velociteam.pspecs.dto;

public class DeviceDTO {
//	 device: {
//    brand: "brand"
//    device: "device"
//    model: "model"
//    id: "id"
//    product: "product"
//}
	private String brand;
	private String device;
	private String model;
	private String id;
	private String product;
	public DeviceDTO() {
	}
	public DeviceDTO(String brand, String device, String model, String id, String product) {
		super();
		this.brand = brand;
		this.device = device;
		this.model = model;
		this.id = id;
		this.product = product;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	
}
