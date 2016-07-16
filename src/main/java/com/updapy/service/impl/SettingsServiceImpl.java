package com.updapy.service.impl;

import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
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

	@Inject
	private UserService userService;

	@Inject
	private AccountRemovalRepository accountRemovalRepository;

	@Override
	public User updateSettings(User user, UpdateSettings newSettings) {
		user.setName(StringUtils.trimToNull(newSettings.getName()));
		user.setLangUpdate(newSettings.getLangUpdate());
		user.setOsVersion(newSettings.getOsVersion());
		user.setDashboardGridSize(newSettings.getDashboardGridSize());
		user.setShowRating(newSettings.getShowRating());
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
			case EMAIL_APP_ADDED:
				setting.setActive(newSettings.isEmailAppAdded());
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
		updateSettings.setLangUpdate(user.getLangUpdate());
		updateSettings.setOsVersion(user.getOsVersion());
		updateSettings.setDashboardGridSize(user.getDashboardGridSize());
		updateSettings.setShowRating(user.getShowRating());
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
			case EMAIL_APP_ADDED:
				updateSettings.setEmailAppAdded(setting.isActive());
				break;
			case NEWSLETTER:
				updateSettings.setEmailNewsletter(setting.isActive());
				break;
			}
		}
		return updateSettings;
	}

	@Override
	public AccountRemoval addFeedback(String feedback) {
		AccountRemoval accountRemoval = new AccountRemoval();
		accountRemoval.setFeedback(feedback);
		accountRemoval.setRemoveDate(new Date());
		return accountRemovalRepository.saveAndFlush(accountRemoval);
	}

	@Override
	public boolean isEmailDisabled(User user) {
		return !isEmailActive(user);
	}

	public boolean isEmailActive(User user) {
		UpdateSettings settings = getSettings(user);
		if (!settings.isEmailAlert() || (settings.isEmailAlert() && (!settings.isEmailEachUpdate() && !settings.isEmailWeekly()))) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isEmailOnEachUpdateActive(User user) {
		UpdateSettings settings = getSettings(user);
		return settings.isEmailAlert() && settings.isEmailEachUpdate();
	}

	@Override
	public boolean isEmailWeeklyActive(User user) {
		UpdateSettings settings = getSettings(user);
		return settings.isEmailAlert() && settings.isEmailWeekly();
	}

	@Override
	public boolean isEmailAppAddedActive(User user) {
		UpdateSettings settings = getSettings(user);
		return settings.isEmailAppAdded();
	}

	@Override
	public boolean isEmailNewsletterActive(User user) {
		UpdateSettings settings = getSettings(user);
		return settings.isEmailNewsletter();
	}
}
