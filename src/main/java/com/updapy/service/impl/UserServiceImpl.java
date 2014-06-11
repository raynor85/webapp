package com.updapy.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.RandomStringUtils;
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

import com.updapy.model.ApplicationFollow;
import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.HelpMessage;
import com.updapy.model.Setting;
import com.updapy.model.User;
import com.updapy.model.UserConnection;
import com.updapy.model.enumeration.Lang;
import com.updapy.model.enumeration.OsVersion;
import com.updapy.model.enumeration.Parameter;
import com.updapy.model.enumeration.SocialMediaService;
import com.updapy.model.enumeration.TypeHelpMessage;
import com.updapy.repository.UserConnectionRepository;
import com.updapy.repository.UserRepository;
import com.updapy.service.ApplicationService;
import com.updapy.service.UserService;
import com.updapy.service.security.SecurityUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConnectionRepository userConnectionRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	ApplicationService applicationService;

	@Override
	public User getCurrentUserLight() {
		return findByEmail(SecurityUtils.getEmailCurrentUser());
	}

	@Override
	public User getCurrentUserWithSettings() {
		User user = getCurrentUserLight();
		// initialize attributes to avoid lazy exception
		Hibernate.initialize(user.getSettings());
		return user;
	}

	@Override
	public User getCurrentUserWithApplicationFolloweds() {
		User user = getCurrentUserLight();
		Hibernate.initialize(user.getFollowedApps());
		return user;
	}

	@Override
	public User getCurrentUserWithHelpMessages() {
		User user = getCurrentUserLight();
		Hibernate.initialize(user.getHelpMessages());
		return user;
	}

	@Override
	public User getCurrentUserFull() {
		User user = getCurrentUserLight();
		Hibernate.initialize(user.getSettings());
		Hibernate.initialize(user.getFollowedApps());
		Hibernate.initialize(user.getHelpMessages());
		Hibernate.initialize(user.getNotifications());
		return user;
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User registerEarly(String email) {
		User user = new User();
		fillDefaultValuesUser(user);
		user.setEarly(true);
		user.setEmail(email);
		return save(user);
	}

	@Override
	public User register(User user) {
		fillDefaultValuesUser(user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEarly(false);
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
		// generate keys
		generateNewApiKeyWithoutSaving(user);
		generateNewAccountKeyWithoutSaving(user);
		// language
		Lang currentLang = Lang.valueOf(LocaleContextHolder.getLocale().getISO3Language());
		user.setLang(currentLang);
		// os version
		user.setOsVersion(OsVersion.WIN_32_BITS);
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

	private void fillDefaultValuesSettings(User user) {
		List<Setting> defaultSettings = new ArrayList<Setting>();
		Setting settingAlertByEmail = new Setting();
		// alert by email...
		settingAlertByEmail.setParameter(Parameter.ALERT_BY_EMAIL);
		settingAlertByEmail.setActive(true);
		settingAlertByEmail.setUser(user);
		defaultSettings.add(settingAlertByEmail);
		Setting settingEmailForEachApplication = new Setting();
		// ...for each app
		settingEmailForEachApplication.setParameter(Parameter.EMAIL_FOR_EACH_APPLICATION);
		settingEmailForEachApplication.setActive(true);
		settingEmailForEachApplication.setUser(user);
		defaultSettings.add(settingEmailForEachApplication);
		Setting settingEmailWeeklyDigest = new Setting();
		// so no weekly digest
		settingEmailWeeklyDigest.setParameter(Parameter.EMAIL_WEEKLY_DIGEST);
		settingEmailWeeklyDigest.setActive(false);
		settingEmailWeeklyDigest.setUser(user);
		defaultSettings.add(settingEmailWeeklyDigest);
		Setting settingNewsletter = new Setting();
		// newsletter subscription
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
			UserConnection userConnection = userConnectionRepository.findByUserConnectionIdUserId(user.getEmail());
			if (userConnection != null) {
				userConnectionRepository.delete(userConnection);
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
		List<ApplicationFollow> applicationFollows = user.getFollowedApps();
		Collections.sort(applicationFollows, new Comparator<ApplicationFollow>() {
			@Override
			public int compare(ApplicationFollow a1, ApplicationFollow a2) {
				return a1.getReferenceApp().getApiName().compareTo(a2.getReferenceApp().getApiName());
			}
		});
		return applicationFollows;
	}

	@Override
	public List<ApplicationFollow> addApplicationsToFollow(User user, List<String> apiNames) {
		List<ApplicationFollow> followedApps = new ArrayList<ApplicationFollow>();
		for (String apiName : apiNames) {
			if (apiName != null) {
				ApplicationFollow applicationFollow = new ApplicationFollow();
				applicationFollow.setReferenceApp(applicationService.getApplicationReference(apiName));
				applicationFollow.setEmailNotificationActive(true);
				applicationFollow.setUser(user);
				followedApps.add(applicationFollow);
				applicationService.saveApplicationFollow(applicationFollow);
			}
		}
		return followedApps;
	}

	@Override
	public boolean deleteApplicationToFollow(User user, String apiName) {
		ApplicationFollow applicationFollow = findApplicationFollow(user, apiName);
		if (applicationFollow == null) {
			return false;
		}
		applicationService.deleteApplicationFollow(applicationFollow);
		return true;
	}

	@Override
	public boolean disableEmailAlertApplicationToFollow(User user, String apiName) {
		ApplicationFollow applicationFollow = findApplicationFollow(user, apiName);
		if (applicationFollow == null) {
			return false;
		}
		applicationService.disableEmailAlertApplicationFollow(applicationFollow);
		return true;
	}

	@Override
	public boolean enableEmailAlertApplicationToFollow(User user, String apiName) {
		ApplicationFollow applicationFollow = findApplicationFollow(user, apiName);
		if (applicationFollow == null) {
			return false;
		}
		applicationService.enableEmailAlertApplicationFollow(applicationFollow);
		return true;
	}

	private ApplicationFollow findApplicationFollow(User user, final String apiName) {
		return (ApplicationFollow) CollectionUtils.find(user.getFollowedApps(), new Predicate() {
			@Override
			public boolean evaluate(Object o) {
				return ((ApplicationFollow) o).getReferenceApp().getApiName().equals(apiName);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApplicationReference> getLeftApplications(User user) {
		List<ApplicationReference> allApplicationReferences = applicationService.getApplicationReferences();
		List<ApplicationReference> followApplicationReferences = (List<ApplicationReference>) CollectionUtils.collect(getFollowedApplications(user), new BeanToPropertyValueTransformer("referenceApp"));
		return (List<ApplicationReference>) CollectionUtils.subtract(allApplicationReferences, followApplicationReferences);
	}

	@Override
	public String getDownloadUrlMatchingSettings(User user, ApplicationVersion applicationVersion) {
		return getDownloadUrlMatchingSettings(applicationVersion, user.getLang(), user.getOsVersion());
	}

	private String getDownloadUrlMatchingSettings(ApplicationVersion applicationVersion, Lang lang, OsVersion osVersion) {
		String defaultUrl = applicationVersion.getWin32UrlEn();
		if (lang == null && osVersion == null || Lang.eng.equals(lang) && osVersion == null || Lang.eng.equals(lang) && OsVersion.WIN_32_BITS.equals(osVersion) || lang == null && OsVersion.WIN_32_BITS.equals(osVersion)) {
			return defaultUrl;
		}
		if (lang == null && OsVersion.WIN_64_BITS.equals(osVersion) || Lang.eng.equals(lang) && OsVersion.WIN_64_BITS.equals(osVersion)) {
			return StringUtils.isNotBlank(applicationVersion.getWin64UrlEn()) ? applicationVersion.getWin64UrlEn() : defaultUrl;
		}
		if (Lang.fra.equals(lang) && osVersion == null || Lang.fra.equals(lang) && OsVersion.WIN_32_BITS.equals(osVersion)) {
			return StringUtils.isNotBlank(applicationVersion.getWin32UrlFr()) ? applicationVersion.getWin32UrlFr() : defaultUrl;
		}
		if (Lang.fra.equals(lang) && OsVersion.WIN_64_BITS.equals(osVersion)) {
			return StringUtils.isNotBlank(applicationVersion.getWin64UrlFr()) ? applicationVersion.getWin64UrlFr() : defaultUrl;
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

}
