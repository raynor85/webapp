package com.updapy.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.updapy.model.common.BaseEntity;
import com.updapy.model.enumeration.Lang;
import com.updapy.model.enumeration.OsVersion;
import com.updapy.model.enumeration.SocialMediaService;

@Entity(name = "person")
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "person_seq")
public class User extends BaseEntity {

	@Column(unique = true)
	private String email;

	private String password;

	private String name;

	@Enumerated(EnumType.STRING)
	private SocialMediaService socialMediaService;

	@Enumerated(EnumType.STRING)
	private Lang lang;

	@Enumerated(EnumType.STRING)
	private OsVersion osVersion;

	// Early user = register before the service was available
	private boolean early;

	private boolean active;

	@Temporal(TemporalType.TIMESTAMP)
	private Date generationAccountKeyDate;

	private String accountKey;

	@Temporal(TemporalType.TIMESTAMP)
	private Date generationApiKeyDate;

	private String apiKey;

	@Temporal(TemporalType.TIMESTAMP)
	private Date activationDate;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private List<HelpMessage> helpMessages;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private List<Setting> settings;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<ApplicationRequest> requestedApps;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<ApplicationFollow> followedApps;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<ApplicationNotification> notifications;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getEarly() {
		return early;
	}

	public void setEarly(boolean early) {
		this.early = early;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<HelpMessage> getHelpMessages() {
		return helpMessages;
	}

	public void setHelpMessages(List<HelpMessage> helpMessages) {
		this.helpMessages = helpMessages;
	}

	public List<Setting> getSettings() {
		return settings;
	}

	public void setSettings(List<Setting> settings) {
		this.settings = settings;
	}

	public List<ApplicationRequest> getRequestedApps() {
		return requestedApps;
	}

	public void setRequestedApps(List<ApplicationRequest> requestedApps) {
		this.requestedApps = requestedApps;
	}

	public List<ApplicationFollow> getFollowedApps() {
		return followedApps;
	}

	public void setFollowedApps(List<ApplicationFollow> followedApps) {
		this.followedApps = followedApps;
	}

	public List<ApplicationNotification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<ApplicationNotification> notifications) {
		this.notifications = notifications;
	}

	public SocialMediaService getSocialMediaService() {
		return socialMediaService;
	}

	public void setSocialMediaService(SocialMediaService socialMediaService) {
		this.socialMediaService = socialMediaService;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getGenerationAccountKeyDate() {
		return generationAccountKeyDate;
	}

	public void setGenerationAccountKeyDate(Date generationAccountKeyDate) {
		this.generationAccountKeyDate = generationAccountKeyDate;
	}

	public String getAccountKey() {
		return accountKey;
	}

	public void setAccountKey(String accountKey) {
		this.accountKey = accountKey;
	}

	public Date getGenerationApiKeyDate() {
		return generationApiKeyDate;
	}

	public void setGenerationApiKeyDate(Date generationApiKeyDate) {
		this.generationApiKeyDate = generationApiKeyDate;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public boolean isSocialUser() {
		return socialMediaService != null;
	}

	public Lang getLang() {
		return lang;
	}

	public void setLang(Lang lang) {
		this.lang = lang;
	}

	public OsVersion getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(OsVersion osVersion) {
		this.osVersion = osVersion;
	}

}
