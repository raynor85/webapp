package com.updapy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

import com.updapy.model.Account;
import com.updapy.model.AccountActivation;
import com.updapy.model.HelpMessage;
import com.updapy.model.Setting;
import com.updapy.model.User;
import com.updapy.model.enumeration.Parameter;
import com.updapy.model.enumeration.SocialMediaService;
import com.updapy.model.enumeration.TypeHelpMessage;
import com.updapy.repository.UserRepository;
import com.updapy.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

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
	public String generateNewKey(User user) {
		String newKey = generateNewKeyWithoutSaving(user);
		save(user);
		return newKey;
	}

	private String generateNewKeyWithoutSaving(User user) {
		String newKey = RandomStringUtils.random(50, "0123456789abcdefghijklmnopqrstuvwxyz");
		user.getAccount().getActivation().setKey(newKey);
		user.getAccount().getActivation().setGenerationKeyDate(new Date());
		return newKey;
	}

	private User save(User user) {
		return userRepository.saveAndFlush(user);
	}

	private void fillDefaultValuesUser(User user) {
		fillDefaultValuesAccount(user);
		fillDefaultValuesSettings(user);
		fillDefaultValuesHelpMessages(user);
	}

	private void fillDefaultValuesAccount(User user) {
		Account account = new Account();
		AccountActivation accountActivation = new AccountActivation();
		// not yet activate
		accountActivation.setActive(false);
		account.setActivation(accountActivation);
		user.setAccount(account);
		// generate the key
		generateNewKeyWithoutSaving(user);
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
		AccountActivation accountActivation = user.getAccount().getActivation();
		accountActivation.setActivationDate(new Date());
		accountActivation.setActive(true);
		return save(user);
	}

	@Override
	public User updatePassword(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		generateNewKeyWithoutSaving(user); // to invalidate the reset link
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
		User user = new User();
		fillDefaultValuesUser(user);
		user.getAccount().getActivation().setActive(true); // active by default
		user.setEmail(profile.getEmail());
		user.setName(profile.getName());
		ConnectionKey providerKey = connection.getKey();
		user.setSocialMediaService(SocialMediaService.valueOf(providerKey.getProviderId().toUpperCase()));
		return save(user);
	}

}
