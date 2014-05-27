package com.updapy.service;

import org.springframework.transaction.annotation.Transactional;

import com.updapy.form.model.UpdateSettings;
import com.updapy.model.AccountRemoval;
import com.updapy.model.User;

@Transactional
public interface SettingsService {

	User updateSettings(User user, UpdateSettings newSettings);

	User updateCurrentSettings(UpdateSettings newSettings);

	UpdateSettings getSettings(User user);

	UpdateSettings getCurrentSettings();

	AccountRemoval addFeedback(String feedback);

}
