package com.updapy.model.api;

import org.springframework.hateoas.ResourceSupport;

public class LatestApplicationVersion extends ResourceSupport {

	private String applicationName;

	private String versionDate;

	private String versionNumber;

	private String win32UrlEn;

	private String win64UrlEn;

	private String win32UrlFr;

	private String win64UrlFr;

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
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

	public String getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(String versionDate) {
		this.versionDate = versionDate;
	}

}
