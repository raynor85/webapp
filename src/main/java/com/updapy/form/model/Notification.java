package com.updapy.form.model;

import com.updapy.model.enumeration.TypeNotification;

public class Notification {

	private String applicationName;

	private String applicationApiName;

	private String versionNumber;

	private String versionDownloadLink;

	private boolean wasRead;

	private TypeNotification type;

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

	public boolean isWasRead() {
		return wasRead;
	}

	public void setWasRead(boolean wasRead) {
		this.wasRead = wasRead;
	}

	public TypeNotification getType() {
		return type;
	}

	public void setType(TypeNotification type) {
		this.type = type;
	}

	public String getApplicationApiName() {
		return applicationApiName;
	}

	public void setApplicationApiName(String applicationApiName) {
		this.applicationApiName = applicationApiName;
	}

	public String getVersionDownloadLink() {
		return versionDownloadLink;
	}

	public void setVersionDownloadLink(String versionDownloadLink) {
		this.versionDownloadLink = versionDownloadLink;
	}

}
