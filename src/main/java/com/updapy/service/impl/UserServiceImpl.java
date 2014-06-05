package com.updapy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

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

	@Override
	public User getCurrentUser() {
		return findByEmail(SecurityUtils.getEmailCurrentUser());
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

	@Override
	public String generateCurrentNewApiKey() {
		return generateNewApiKey(getCurrentUser());
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

	private String generateNewApiKey(User user) {
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
		HelpMessage helpMessageSearchHowTo = new HelpMessage();
		helpMessageSearchHowTo.setType(TypeHelpMessage.SEARCH_HOW_TO);
		helpMessageSearchHowTo.setHidden(false);
		helpMessageSearchHowTo.setUser(user);
		defaultHelpMessages.add(helpMessageSearchHowTo);
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
	public User updateCurrentPassword(String newPassword) {
		return updatePassword(getCurrentUser(), newPassword);
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
	public boolean deleteCurrentUser() {
		return delete(getCurrentUser());
	}

	@Override
	public boolean isValidApiKey(String key) {
		return userRepository.findByApiKey(key) != null;
	}

}
