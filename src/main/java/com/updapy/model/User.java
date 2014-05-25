package com.updapy.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.updapy.model.common.BaseEntity;
import com.updapy.model.enumeration.SocialMediaService;

@Entity(name = "person")
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "person_seq")
public class User extends BaseEntity {

	private String email;

	private String password;

	private String name;

	@Enumerated(EnumType.STRING)
	private SocialMediaService socialMediaService;

	// Early user = register before the service was available
	private boolean early;

	private boolean active;

	@Temporal(TemporalType.TIMESTAMP)
	private Date generationKeyDate;

	private String key;

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

	public Date getGenerationKeyDate() {
		return generationKeyDate;
	}

	public void setGenerationKeyDate(Date generationKeyDate) {
		this.generationKeyDate = generationKeyDate;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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
}
