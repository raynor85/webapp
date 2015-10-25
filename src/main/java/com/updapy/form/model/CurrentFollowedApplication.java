package com.updapy.form.model;

public class CurrentFollowedApplication {

	private String applicationName;

	private String versionNumber;

	private String apiName;

	private String downloadUrl;

	private String websiteUrl;

	private String iconFilename;

	private boolean emailNotificationActive;

	private Integer rating;

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

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getIconFilename() {
		return iconFilename;
	}

	public void setIconFilename(String iconFilename) {
		this.iconFilename = iconFilename;
	}

	public boolean isEmailNotificationActive() {
		return emailNotificationActive;
	}

	public void setEmailNotificationActive(boolean emailNotificationActive) {
		this.emailNotificationActive = emailNotificationActive;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

}
