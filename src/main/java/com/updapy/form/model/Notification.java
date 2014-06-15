package com.updapy.form.model;

import com.updapy.model.enumeration.TypeNofication;

public class Notification {

	private String applicationName;

	private String versionNumber;

	private boolean wasRead;

	private TypeNofication type;

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

	public TypeNofication getType() {
		return type;
	}

	public void setType(TypeNofication type) {
		this.type = type;
	}

}
