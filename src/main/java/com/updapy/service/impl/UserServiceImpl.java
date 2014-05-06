package com.updapy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.updapy.model.Account;
import com.updapy.model.AccountActivation;
import com.updapy.model.HelpMessage;
import com.updapy.model.Setting;
import com.updapy.model.User;
import com.updapy.model.enumeration.Parameter;
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
	public void registerEarly(String email) {
		User user = new User();
		fillDefaultValuesUser(user);
		user.setEarly(true);
		user.setEmail(email);
		userRepository.saveAndFlush(user);
	}

	@Override
	public void register(User user) {
		fillDefaultValuesUser(user);
		// Encrypt password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEarly(false);
		userRepository.saveAndFlush(user);
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
		// generate the key
		accountActivation.setKey(RandomStringUtils.random(50, "0123456789abcdefghijklmnopqrstuvwxyz"));
		accountActivation.setGenerationKeyDate(new Date());
		account.setActivation(accountActivation);
		user.setAccount(account);
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

}
