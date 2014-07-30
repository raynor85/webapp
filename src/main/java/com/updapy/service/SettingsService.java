package com.updapy.service;

import org.springframework.transaction.annotation.Transactional;

import com.updapy.form.model.UpdateSettings;
import com.updapy.model.AccountRemoval;
import com.updapy.model.User;

@Transactional
public interface SettingsService {

	User updateSettings(User user, UpdateSettings newSettings);

	UpdateSettings getSettings(User user);

	AccountRemoval addFeedback(String feedback);

	boolean isEmailDisabled(User user);

	boolean isEmailOnEachUpdateActive(User user);

	boolean isEmailWeeklyActive(User user);

	boolean isEmailAppAddedActive(User user);

	boolean isEmailNewsletterActive(User user);

}
