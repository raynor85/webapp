package com.updapy.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.updapy.form.model.UpdateSettings;
import com.updapy.model.AccountRemoval;
import com.updapy.model.Setting;
import com.updapy.model.User;
import com.updapy.model.enumeration.Parameter;
import com.updapy.repository.AccountRemovalRepository;
import com.updapy.service.SettingsService;
import com.updapy.service.UserService;

@Service
public class SettingsServiceImpl implements SettingsService {

	@Autowired
	UserService userService;

	@Autowired
	AccountRemovalRepository accountRemovalRepository;

	@Override
	public User updateSettings(User user, UpdateSettings newSettings) {
		user.setName(StringUtils.trimToNull(newSettings.getName()));
		user.setLang(newSettings.getLang());
		user.setOsVersion(newSettings.getOsVersion());
		for (Setting setting : user.getSettings()) {
			Parameter parameter = setting.getParameter();
			switch (parameter) {
			case ALERT_BY_EMAIL:
				setting.setActive(newSettings.isEmailAlert());
				break;
			case EMAIL_FOR_EACH_APPLICATION:
				setting.setActive(newSettings.isEmailEachUpdate());
				break;
			case EMAIL_WEEKLY_DIGEST:
				setting.setActive(newSettings.isEmailWeekly());
				break;
			case NEWSLETTER:
				setting.setActive(newSettings.isEmailNewsletter());
				break;
			}
		}
		return userService.save(user);
	}

	@Override
	public UpdateSettings getSettings(User user) {
		UpdateSettings updateSettings = new UpdateSettings();
		updateSettings.setName(user.getName());
		updateSettings.setLang(user.getLang());
		updateSettings.setOsVersion(user.getOsVersion());
		for (Setting setting : user.getSettings()) {
			Parameter parameter = setting.getParameter();
			switch (parameter) {
			case ALERT_BY_EMAIL:
				updateSettings.setEmailAlert(setting.isActive());
				break;
			case EMAIL_FOR_EACH_APPLICATION:
				updateSettings.setEmailEachUpdate(setting.isActive());
				break;
			case EMAIL_WEEKLY_DIGEST:
				updateSettings.setEmailWeekly(setting.isActive());
				break;
			case NEWSLETTER:
				updateSettings.setEmailNewsletter(setting.isActive());
				break;
			}
		}
		return updateSettings;
	}

	@Override
	public UpdateSettings getCurrentSettings() {
		return getSettings(userService.getCurrentUser());
	}

	@Override
	public User updateCurrentSettings(UpdateSettings newSettings) {
		return updateSettings(userService.getCurrentUser(), newSettings);
	}

	@Override
	public AccountRemoval addFeedback(String feedback) {
		AccountRemoval accountRemoval = new AccountRemoval();
		accountRemoval.setFeedback(feedback);
		accountRemoval.setRemoveDate(new Date());
		return accountRemovalRepository.saveAndFlush(accountRemoval);
	}

}
