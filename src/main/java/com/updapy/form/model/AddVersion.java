package com.updapy.form.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

public class AddVersion {

	@NotEmpty
	private String apiName;

	@NotEmpty
	@URL
	private String win32UrlEn;

	@URL
	private String win64UrlEn;

	@URL
	private String win32UrlFr;

	@URL
	private String win64UrlFr;

	@NotEmpty
	private String versionNumber;

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getWin32UrlEn() {
		return win32UrlEn;
	}

	public void setWin32UrlEn(String win32UrlEn) {
		this.win32UrlEn = win32UrlEn;
	}

	public String getWin64UrlEn() {
		return win64UrlEn;
	}

	public void setWin64UrlEn(String win64UrlEn) {
		this.win64UrlEn = win64UrlEn;
	}

	public String getWin32UrlFr() {
		return win32UrlFr;
	}

	public void setWin32UrlFr(String win32UrlFr) {
		this.win32UrlFr = win32UrlFr;
	}

	public String getWin64UrlFr() {
		return win64UrlFr;
	}

	public void setWin64UrlFr(String win64UrlFr) {
		this.win64UrlFr = win64UrlFr;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getApiNameFormatted() {
		return apiName.toLowerCase().replaceAll("\\s+", "");
	}

}
