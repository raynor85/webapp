package com.updapy.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

import com.updapy.form.model.Notification;
import com.updapy.form.model.UpdateUrl;
import com.updapy.model.ApplicationFollow;
import com.updapy.model.ApplicationNotification;
import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.HelpMessage;
import com.updapy.model.Setting;
import com.updapy.model.User;
import com.updapy.model.UserConnection;
import com.updapy.model.enumeration.DashboardGridSize;
import com.updapy.model.enumeration.Lang;
import com.updapy.model.enumeration.OsVersion;
import com.updapy.model.enumeration.Parameter;
import com.updapy.model.enumeration.Profile;
import com.updapy.model.enumeration.SocialMediaService;
import com.updapy.model.enumeration.TypeHelpMessage;
import com.updapy.model.enumeration.TypeNotification;
import com.updapy.repository.UserConnectionRepository;
import com.updapy.repository.UserRepository;
import com.updapy.service.ApplicationService;
import com.updapy.service.EmailAddedApplicationService;
import com.updapy.service.EmailDeletedApplicationService;
import com.updapy.service.EmailSingleUpdateService;
import com.updapy.service.EmailWeeklyUpdateService;
import com.updapy.service.SettingsService;
import com.updapy.service.UserService;
import com.updapy.service.security.SecurityUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConnectionRepository userConnectionRepository;

	@Autowired(required = false)
	private AuthenticationManager authenticationManager;

	@Autowired(required = false)
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private SettingsService settingsService;

	@Autowired
	private EmailSingleUpdateService emailSingleUpdateService;

	@Autowired
	private EmailWeeklyUpdateService emailWeeklyUpdateService;

	@Autowired
	private EmailAddedApplicationService emailAddedApplicationService;

	@Autowired
	private EmailDeletedApplicationService emailDeletedApplicationService;

	@Override
	public User getCurrentUserLight() {
		String email = SecurityUtils.getEmailCurrentUser();
		if (StringUtils.isBlank(email)) {
			return null;
		}
		return findByEmail(email);
	}

	@Override
	public User getCurrentUserWithSettings() {
		User user = getCurrentUserLight();
		if (user == null) {
			return null;
		}
		// initialize attributes to avoid lazy exception
		Hibernate.initialize(user.getSettings());
		return user;
	}

	@Override
	public User getCurrentUserWithApplicationFolloweds() {
		User user = getCurrentUserLight();
		if (user == null) {
			return null;
		}
		Hibernate.initialize(user.getFollowedApplications());
		return user;
	}

	@Override
	public User getCurrentUserWithHelpMessages() {
		User user = getCurrentUserLight();
		if (user == null) {
			return null;
		}
		Hibernate.initialize(user.getHelpMessages());
		return user;
	}

	@Override
	public User getCurrentUserFull() {
		User user = getCurrentUserLight();
		if (user == null) {
			return null;
		}
		Hibernate.initialize(user.getSettings());
		Hibernate.initialize(user.getFollowedApplications());
		Hibernate.initialize(user.getHelpMessages());
		return user;
	}

	@Override
	public User getUserLightFromRssKey(String key) {
		return userRepository.findByRssKey(key);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void changeLocale(Locale locale) {
		User user = getCurrentUserLight();
		if (user != null) {
			user.setLangEmail(locale);
			save(user);
		}
	}

	@Override
	public User register(User user) {
		fillDefaultValuesUser(user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return save(user);
	}

	@Override
	public String generateNewAccountKey(User user) {
		String newKey = generateNewAccountKeyWithoutSaving(user);
		save(user);
		return newKey;
	}

	private String generateNewAccountKeyWithoutSaving(User user) {
		String newKey = generateKey();
		user.setAccountKey(newKey);
		user.setGenerationAccountKeyDate(new Date());
		return newKey;
	}

	private String generateKey() {
		String newKey = RandomStringUtils.random(50, "0123456789abcdefghijklmnopqrstuvwxyz");
		return newKey;
	}

	private void fillDefaultValuesUser(User user) {
		fillDefaultValuesAccount(user);
		fillDefaultValuesSettings(user);
		fillDefaultValuesHelpMessages(user);
	}

	private void fillDefaultValuesAccount(User user) {
		// not yet activate
		user.setActive(false);
		// user profile
		user.setProfile(Profile.USER);
		// generate keys
		generateNewApiKeyWithoutSaving(user);
		generateNewRssKeyWithoutSaving(user);
		generateNewAccountKeyWithoutSaving(user);
		// language
		Locale currentLang = LocaleContextHolder.getLocale();
		user.setLangUpdate(Lang.valueOf(currentLang.toLanguageTag()));
		user.setLangEmail(currentLang);
		// os version
		user.setOsVersion(OsVersion.WIN_32_BITS);
		// big icons
		user.setDashboardGridSize(DashboardGridSize.BIG);
	}

	@Override
	public String generateNewApiKey(User user) {
		String newKey = generateNewApiKeyWithoutSaving(user);
		save(user);
		return newKey;
	}

	private String generateNewApiKeyWithoutSaving(User user) {
		String newKey = generateKey();
		user.setApiKey(newKey);
		user.setGenerationApiKeyDate(new Date());
		return newKey;
	}

	private String generateNewRssKeyWithoutSaving(User user) {
		String newKey = generateKey();
		user.setRssKey(newKey);
		user.setGenerationRssKeyDate(new Date());
		return newKey;
	}

	private void fillDefaultValuesSettings(User user) {
		List<Setting> defaultSettings = new ArrayList<Setting>();

		// alert by email...
		Setting settingAlertByEmail = new Setting();
		settingAlertByEmail.setParameter(Parameter.ALERT_BY_EMAIL);
		settingAlertByEmail.setActive(true);
		settingAlertByEmail.setUser(user);
		defaultSettings.add(settingAlertByEmail);

		// ...for each app
		Setting settingEmailForEachApplication = new Setting();
		settingEmailForEachApplication.setParameter(Parameter.EMAIL_FOR_EACH_APPLICATION);
		settingEmailForEachApplication.setActive(true);
		settingEmailForEachApplication.setUser(user);
		defaultSettings.add(settingEmailForEachApplication);

		// so no weekly digest
		Setting settingEmailWeeklyDigest = new Setting();
		settingEmailWeeklyDigest.setParameter(Parameter.EMAIL_WEEKLY_DIGEST);
		settingEmailWeeklyDigest.setActive(false);
		settingEmailWeeklyDigest.setUser(user);
		defaultSettings.add(settingEmailWeeklyDigest);

		// send email for added apps
		Setting settingEmailAppAdded = new Setting();
		settingEmailAppAdded.setParameter(Parameter.EMAIL_APP_ADDED);
		settingEmailAppAdded.setActive(true);
		settingEmailAppAdded.setUser(user);
		defaultSettings.add(settingEmailAppAdded);

		// newsletter subscription
		Setting settingNewsletter = new Setting();
		settingNewsletter.setParameter(Parameter.NEWSLETTER);
		settingNewsletter.setActive(true);
		settingNewsletter.setUser(user);
		defaultSettings.add(settingNewsletter);
		user.setSettings(defaultSettings);
	}

	private void fillDefaultValuesHelpMessages(User user) {
		// all is visible at beginning
		List<HelpMessage> defaultHelpMessages = new ArrayList<HelpMessage>();
		HelpMessage helpMessageDashboardHowTo = new HelpMessage();
		helpMessageDashboardHowTo.setType(TypeHelpMessage.DASHBOARD_HOW_TO);
		helpMessageDashboardHowTo.setHidden(false);
		helpMessageDashboardHowTo.setUser(user);
		defaultHelpMessages.add(helpMessageDashboardHowTo);
		HelpMessage helpMessageDashboardAlertDisabled = new HelpMessage();
		helpMessageDashboardAlertDisabled.setType(TypeHelpMessage.DASHBOARD_ALERT_DISABLED);
		helpMessageDashboardAlertDisabled.setHidden(false);
		helpMessageDashboardAlertDisabled.setUser(user);
		defaultHelpMessages.add(helpMessageDashboardAlertDisabled);
		user.setHelpMessages(defaultHelpMessages);
	}

	@Override
	public User activate(User user) {
		user.setActivationDate(new Date());
		user.setActive(true);
		return save(user);
	}

	@Override
	public User updatePassword(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		generateNewAccountKeyWithoutSaving(user); // to invalidate the reset link
		return save(user);
	}

	@Override
	public User updateEmail(User user, String newEmail) {
		user.setEmail(newEmail);
		user.setActive(false);
		user.setActivationDate(null);
		generateNewAccountKeyWithoutSaving(user); // to invalidate the reset link
		return save(user);
	}

	@Override
	public User registerSocial(Connection<?> connection) {
		if (connection == null) {
			return null;
		}
		UserProfile profile = connection.fetchUserProfile();
		if (profile == null || profile.getEmail() == null) {
			return null;
		}
		User userExisting = userRepository.findByEmail(profile.getEmail());
		if (userExisting != null) {
			// user is already existing, overwrite some parameters
			userExisting.setName(profile.getName());
			userExisting.setSocialMediaService(getSocialMediaService(connection));
			return save(userExisting);
		}
		User user = new User();
		fillDefaultValuesUser(user);
		user.setActive(true); // active by default
		user.setEmail(profile.getEmail());
		user.setName(profile.getName());
		user.setSocialMediaService(getSocialMediaService(connection));
		return save(user);
	}

	private SocialMediaService getSocialMediaService(Connection<?> connection) {
		ConnectionKey providerKey = connection.getKey();
		return SocialMediaService.valueOf(providerKey.getProviderId().toUpperCase());
	}

	@Override
	public User save(User user) {
		return userRepository.saveAndFlush(user);
	}

	@Override
	public boolean isCurrentPassword(String password) {
		return SecurityUtils.isPasswordCorrectForCurrentUser(authenticationManager, password);
	}

	@Override
	public boolean delete(User user) {
		try {
			// remove social data
			List<UserConnection> userConnections = userConnectionRepository.findByUserConnectionIdUserId(user.getEmail());
			if (userConnections != null && !userConnections.isEmpty()) {
				userConnectionRepository.delete(userConnections);
			}
			// remove other data
			userRepository.delete(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean isValidApiKey(String key) {
		return userRepository.findByApiKey(key) != null;
	}

	@Override
	public List<ApplicationFollow> getFollowedApplications(User user) {
		List<ApplicationFollow> followedApplications = user.getFollowedApplications();
		CollectionUtils.filter(followedApplications, new Predicate() {
			@Override
			public boolean evaluate(Object followedApplication) {
				return ((ApplicationFollow) followedApplication).getApplication().isActive();
			}
		});
		Collections.sort(followedApplications, new Comparator<ApplicationFollow>() {
			@Override
			public int compare(ApplicationFollow a1, ApplicationFollow a2) {
				return a1.getApplication().getApiName().compareTo(a2.getApplication().getApiName());
			}
		});
		return followedApplications;
	}

	@Override
	public List<ApplicationFollow> addFollowedApplications(User user, List<String> apiNames) {
		List<ApplicationFollow> followedApplications = new ArrayList<ApplicationFollow>();
		for (String apiName : apiNames) {
			if (apiName != null) {
				ApplicationFollow followedApplication = new ApplicationFollow();
				followedApplication.setApplication(applicationService.getApplication(apiName));
				followedApplication.setEmailNotificationActive(true);
				followedApplication.setUser(user);
				followedApplications.add(followedApplication);
				applicationService.saveFollowedApplication(followedApplication);
			}
		}
		return followedApplications;
	}

	@Override
	public boolean deleteFollowedApplication(User user, String apiName) {
		ApplicationFollow followedApplication = findFollowedApplication(user, apiName);
		if (followedApplication == null) {
			return false;
		}
		removeCurrentNotificationsForApplication(user, followedApplication.getApplication());
		applicationService.deleteFollowedApplication(followedApplication);
		return true;
	}

	@Override
	public boolean disableEmailAlertFollowedApplication(User user, String apiName) {
		ApplicationFollow followedApplication = findFollowedApplication(user, apiName);
		if (followedApplication == null) {
			return false;
		}
		applicationService.disableEmailAlertFollowedApplication(followedApplication);
		return true;
	}

	@Override
	public boolean enableEmailAlertFollowedApplication(User user, String apiName) {
		ApplicationFollow followedApplication = findFollowedApplication(user, apiName);
		if (followedApplication == null) {
			return false;
		}
		applicationService.enableEmailAlertFollowedApplication(followedApplication);
		return true;
	}

	private ApplicationFollow findFollowedApplication(User user, final String apiName) {
		return (ApplicationFollow) CollectionUtils.find(user.getFollowedApplications(), new Predicate() {
			@Override
			public boolean evaluate(Object o) {
				return ((ApplicationFollow) o).getApplication().getApiName().equals(apiName);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApplicationReference> getLeftApplications(User user) {
		List<ApplicationReference> allApplications = applicationService.getApplications();
		List<ApplicationReference> followedApplications = (List<ApplicationReference>) CollectionUtils.collect(getFollowedApplications(user), new BeanToPropertyValueTransformer("application"));
		return (List<ApplicationReference>) CollectionUtils.subtract(allApplications, followedApplications);
	}

	@Override
	public UpdateUrl getDownloadUrlMatchingSettings(User user, ApplicationVersion version) {
		return getDownloadUrlMatchingSettings(version, user.getLangUpdate(), user.getOsVersion());
	}

	private UpdateUrl getDownloadUrlMatchingSettings(ApplicationVersion appVersion, Lang lang, OsVersion osVersion) {

		UpdateUrl defaultUrl = new UpdateUrl(appVersion.getWin32UrlEn(), Lang.en, OsVersion.WIN_32_BITS);
		if (OsVersion.WIN_64_BITS.equals(osVersion) && StringUtils.isNotBlank(appVersion.getWin64UrlEn())) {
			defaultUrl = new UpdateUrl(appVersion.getWin64UrlEn(), Lang.en, OsVersion.WIN_64_BITS);
		} else if (Lang.fr.equals(lang) && StringUtils.isNotBlank(appVersion.getWin32UrlFr())) {
			defaultUrl = new UpdateUrl(appVersion.getWin32UrlFr(), Lang.fr, OsVersion.WIN_32_BITS);
		}

		if (lang == null && osVersion == null || Lang.en.equals(lang) && osVersion == null || Lang.en.equals(lang) && OsVersion.WIN_32_BITS.equals(osVersion) || lang == null && OsVersion.WIN_32_BITS.equals(osVersion)) {
			return defaultUrl;
		}
		if (lang == null && OsVersion.WIN_64_BITS.equals(osVersion) || Lang.en.equals(lang) && OsVersion.WIN_64_BITS.equals(osVersion)) {
			return StringUtils.isNotBlank(appVersion.getWin64UrlEn()) ? new UpdateUrl(appVersion.getWin64UrlEn(), Lang.en, OsVersion.WIN_64_BITS) : defaultUrl;
		}
		if (Lang.fr.equals(lang) && osVersion == null || Lang.fr.equals(lang) && OsVersion.WIN_32_BITS.equals(osVersion)) {
			return StringUtils.isNotBlank(appVersion.getWin32UrlFr()) ? new UpdateUrl(appVersion.getWin32UrlFr(), Lang.fr, OsVersion.WIN_32_BITS) : defaultUrl;
		}
		if (Lang.fr.equals(lang) && OsVersion.WIN_64_BITS.equals(osVersion)) {
			return StringUtils.isNotBlank(appVersion.getWin64UrlFr()) ? new UpdateUrl(appVersion.getWin64UrlFr(), Lang.fr, OsVersion.WIN_64_BITS) : defaultUrl;
		}
		return defaultUrl;
	}

	private HelpMessage findHelpMessageFromType(User user, final TypeHelpMessage typeHelpMessage) {
		return (HelpMessage) CollectionUtils.find(user.getHelpMessages(), new Predicate() {
			@Override
			public boolean evaluate(Object o) {
				return ((HelpMessage) o).getType().equals(typeHelpMessage);
			}
		});
	}

	@Override
	public boolean isMessageDismissed(User user, TypeHelpMessage typeHelpMessage) {
		return findHelpMessageFromType(user, typeHelpMessage).isHidden();
	}

	@Override
	public User dismissMessage(User user, TypeHelpMessage typeHelpMessage) {
		findHelpMessageFromType(user, typeHelpMessage).setHidden(true);
		return save(user);
	}

	@Override
	public List<User> findUsersActive() {
		return userRepository.findByActiveTrue();
	}

	@Override
	public List<User> findUsersFollowingApplication(ApplicationReference application) {
		return userRepository.findByFollowedApplicationsApplication(application);
	}

	@Override
	public List<User> findUsersFollowingNewsletters() {
		List<User> users = findUsersActive();
		CollectionUtils.filter(users, new Predicate() {
			@Override
			public boolean evaluate(Object user) {
				return settingsService.isEmailNewsletterActive((User) user);
			}
		});
		return users;
	}

	@Override
	public void notifyForNewVersion(User user, ApplicationVersion newVersion) {
		removeCurrentNotificationsForApplication(user, newVersion);
		ApplicationNotification notification = new ApplicationNotification();
		notification.setRead(false);
		notification.setVersion(newVersion);
		notification.setType(TypeNotification.NEW_VERSION);
		notification.setUser(user);
		applicationService.saveNotification(notification);
		if (settingsService.isEmailOnEachUpdateActive(user) && isEmailAlertFollowedApplicationEnabled(user, newVersion.getApplication().getApiName())) {
			emailSingleUpdateService.addEmailSingleUpdate(user, newVersion);
		}
	}

	@Override
	public void notifyForAddedApplication(User user, ApplicationReference addedApplication) {
		ApplicationNotification notification = new ApplicationNotification();
		notification.setRead(false);
		notification.setApplication(addedApplication);
		notification.setType(TypeNotification.NEW_APPLICATION);
		notification.setUser(user);
		applicationService.saveNotification(notification);
	}

	@Override
	public void notifyForDeletedApplication(User user, ApplicationReference deletedApplication) {
		ApplicationNotification notification = new ApplicationNotification();
		notification.setRead(false);
		notification.setApplication(deletedApplication);
		notification.setType(TypeNotification.NOT_SUPPORTED_APPLICATION);
		notification.setUser(user);
		applicationService.saveNotification(notification);
		emailDeletedApplicationService.addEmailDeletedApplication(user, deletedApplication);
	}

	private boolean isEmailAlertFollowedApplicationEnabled(User user, String apiName) {
		ApplicationFollow followedApplication = findFollowedApplication(user, apiName);
		if (followedApplication == null) {
			return false;
		}
		return followedApplication.isEmailNotificationActive();
	}

	private void removeCurrentNotificationsForApplication(User user, ApplicationVersion version) {
		removeCurrentNotificationsForApplication(user, version.getApplication());
	}

	private void removeCurrentNotificationsForApplication(User user, ApplicationReference application) {
		List<ApplicationNotification> notifications = applicationService.getNotifications(user, application);
		if (notifications != null && !notifications.isEmpty()) {
			applicationService.deleteNotifications(notifications);
		}
	}

	@Override
	public Long getNbNotifications(User user) {
		return applicationService.countNewNotifications(user);
	}

	@Override
	public List<ApplicationNotification> getLastNbNotifications(User user, int nb) {
		return applicationService.getNbLastNotifications(user, nb);
	}

	@Override
	public boolean markAsReadAllNotifications(User user) {
		return applicationService.markAsReadAllNotifications(user);
	}

	@Override
	public void addDownloadLinksToNotifications(List<Notification> notifications, User user) {
		for (Notification notification : notifications) {
			if (TypeNotification.NEW_VERSION.equals(notification.getType())) {
				notification.setVersionDownloadLink(getDownloadUrlMatchingSettings(user, applicationService.getLatestVersion(notification.getApplicationApiName())).getUrl());
			}
		}
	}

	@Override
	public String getAvatarUrl(User user) {
		List<UserConnection> userConnections = userConnectionRepository.findByUserConnectionIdUserId(user.getEmail());
		if (userConnections != null && !userConnections.isEmpty()) {
			return userConnections.get(0).getImageUrl();
		}
		return "http://www.updapy.com/resources/img/dashboard/user-normal.png";
	}

}
