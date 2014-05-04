package com.updapy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void registerEarly(String email) {
		User user = initDefaultValuesUser();
		user.setEarly(true);
		user.setEmail(email);
		userRepository.saveAndFlush(user);
	}

	private User initDefaultValuesUser() {
		User user = new User();

		// Account - not yet activate + generate the activation link
		Account account = new Account();
		AccountActivation accountActivation = new AccountActivation();
		accountActivation.setActive(false);
		accountActivation.setKey(RandomStringUtils.random(50, "0123456789abcdefghijklmnopqrstuvwxyz"));
		accountActivation.setGenerationKeyDate(new Date());
		account.setActivation(accountActivation);
		user.setAccount(account);

		// Settings - alert by email for each app + newsletter subscription
		List<Setting> defaultSettings = new ArrayList<Setting>();
		Setting settingAlertByEmail = new Setting();
		settingAlertByEmail.setParameter(Parameter.ALERT_BY_EMAIL);
		settingAlertByEmail.setActive(true);
		settingAlertByEmail.setUser(user);
		defaultSettings.add(settingAlertByEmail);
		Setting settingEmailForEachApplication = new Setting();
		settingEmailForEachApplication.setParameter(Parameter.EMAIL_FOR_EACH_APPLICATION);
		settingEmailForEachApplication.setActive(true);
		settingEmailForEachApplication.setUser(user);
		defaultSettings.add(settingEmailForEachApplication);
		Setting settingEmailWeeklyDigest = new Setting();
		settingEmailWeeklyDigest.setParameter(Parameter.EMAIL_WEEKLY_DIGEST);
		settingEmailWeeklyDigest.setActive(false);
		settingEmailWeeklyDigest.setUser(user);
		defaultSettings.add(settingEmailWeeklyDigest);
		Setting settingNewsletter = new Setting();
		settingNewsletter.setParameter(Parameter.NEWSLETTER);
		settingNewsletter.setActive(true);
		settingNewsletter.setUser(user);
		defaultSettings.add(settingNewsletter);
		user.setSettings(defaultSettings);

		// Help messages - all is visible at beginning
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

		return user;
	}

}
