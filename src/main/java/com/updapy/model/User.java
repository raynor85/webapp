package com.updapy.model;

import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.updapy.model.enumeration.DashboardGridSize;
import com.updapy.model.enumeration.Lang;
import com.updapy.model.enumeration.OsVersion;
import com.updapy.model.enumeration.Profile;
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
	private Lang langUpdate;

	private Locale langEmail;

	@Enumerated(EnumType.STRING)
	private OsVersion osVersion;

	private boolean active;

	@Temporal(TemporalType.TIMESTAMP)
	private Date generationAccountKeyDate;

	private String accountKey;

	@Temporal(TemporalType.TIMESTAMP)
	private Date generationApiKeyDate;

	private String apiKey;

	@Temporal(TemporalType.TIMESTAMP)
	private Date generationRssKeyDate;

	private String rssKey;

	@Temporal(TemporalType.TIMESTAMP)
	private Date activationDate;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private List<HelpMessage> helpMessages;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private List<Setting> settings;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE })
	private List<ApplicationRequest> requestedApplications;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE })
	private List<ApplicationFollow> followedApplications;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE })
	private List<ApplicationNotification> notifications;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private List<EmailSingleUpdate> emailSingleUpdates;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private List<EmailWeeklyUpdate> emailWeeklyUpdates;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private List<EmailNewsletter> emailNewsletters;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private List<EmailAddedApplication> emailAddedApplications;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private List<EmailDeletedApplication> emailDeletedApplications;

	@Enumerated(EnumType.STRING)
	private DashboardGridSize dashboardGridSize;

	@Enumerated(EnumType.STRING)
	private Profile profile;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public List<ApplicationRequest> getRequestedApplications() {
		return requestedApplications;
	}

	public void setRequestedApplications(List<ApplicationRequest> requestedApplications) {
		this.requestedApplications = requestedApplications;
	}

	public List<ApplicationFollow> getFollowedApplications() {
		return followedApplications;
	}

	public void setFollowedApplications(List<ApplicationFollow> followedApplications) {
		this.followedApplications = followedApplications;
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

	public Date getGenerationRssKeyDate() {
		return generationRssKeyDate;
	}

	public void setGenerationRssKeyDate(Date generationRssKeyDate) {
		this.generationRssKeyDate = generationRssKeyDate;
	}

	public String getRssKey() {
		return rssKey;
	}

	public void setRssKey(String rssKey) {
		this.rssKey = rssKey;
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

	public OsVersion getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(OsVersion osVersion) {
		this.osVersion = osVersion;
	}

	public Lang getLangUpdate() {
		return langUpdate;
	}

	public void setLangUpdate(Lang langUpdate) {
		this.langUpdate = langUpdate;
	}

	public Locale getLangEmail() {
		return langEmail;
	}

	public void setLangEmail(Locale langEmail) {
		this.langEmail = langEmail;
	}

	public List<EmailSingleUpdate> getEmailSingleUpdates() {
		return emailSingleUpdates;
	}

	public void setEmailSingleUpdates(List<EmailSingleUpdate> emailSingleUpdates) {
		this.emailSingleUpdates = emailSingleUpdates;
	}

	public List<EmailWeeklyUpdate> getEmailWeeklyUpdates() {
		return emailWeeklyUpdates;
	}

	public void setEmailWeeklyUpdates(List<EmailWeeklyUpdate> emailWeeklyUpdates) {
		this.emailWeeklyUpdates = emailWeeklyUpdates;
	}

	public List<EmailNewsletter> getEmailNewsletters() {
		return emailNewsletters;
	}

	public void setEmailNewsletters(List<EmailNewsletter> emailNewsletters) {
		this.emailNewsletters = emailNewsletters;
	}

	public List<EmailAddedApplication> getEmailAddedApplications() {
		return emailAddedApplications;
	}

	public void setEmailAddedApplications(List<EmailAddedApplication> emailAddedApplications) {
		this.emailAddedApplications = emailAddedApplications;
	}

	public List<EmailDeletedApplication> getEmailDeletedApplications() {
		return emailDeletedApplications;
	}

	public void setEmailDeletedApplications(List<EmailDeletedApplication> emailDeletedApplications) {
		this.emailDeletedApplications = emailDeletedApplications;
	}

	public DashboardGridSize getDashboardGridSize() {
		return dashboardGridSize;
	}

	public void setDashboardGridSize(DashboardGridSize dashboardGridSize) {
		this.dashboardGridSize = dashboardGridSize;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
