package com.velociteam.pspecs.dto;

public class FirmwareDTO {
//	 firmware: {
//    skd: "skd"
//    release: "release"
//    incremental: "incremental"
//}
	private String sdk;
	private String release;
	private String incremental;
	public FirmwareDTO() {}
	public FirmwareDTO(String sdk, String release, String incremental) {
		super();
		this.sdk = sdk;
		this.release = release;
		this.incremental = incremental;
	}
	public String getSdk() {
		return sdk;
	}
	public void setSdk(String sdk) {
		this.sdk = sdk;
	}
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
	}
	public String getIncremental() {
		return incremental;
	}
	public void setIncremental(String incremental) {
		this.incremental = incremental;
	}
}
