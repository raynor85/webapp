package com.updapy.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.BaseEntity;

@Entity(name = "person")
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "person_seq")
public class User extends BaseEntity {

	private String email;

	private String password;

	private String name;

	// Early user = register before the service was available
	private boolean early;

	@OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
	private Account account;

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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
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

}
