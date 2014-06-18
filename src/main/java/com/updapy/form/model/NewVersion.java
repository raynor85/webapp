package com.updapy.form.model;

public class NewVersion {

	private String applicationName;

	private String versionNumber;

	private UpdateUrl updateUrl;

	public NewVersion(String versionNumber, String applicationName, UpdateUrl updateUrl) {
		this.versionNumber = versionNumber;
		this.applicationName = applicationName;
		this.updateUrl = updateUrl;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public UpdateUrl getUpdateUrl() {
		return updateUrl;
	}

	public void setUpdateUrl(UpdateUrl updateUrl) {
		this.updateUrl = updateUrl;
	}

}
