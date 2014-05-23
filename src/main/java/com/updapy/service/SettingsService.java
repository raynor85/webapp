package com.updapy.service;

import com.updapy.form.model.UpdateSettings;
import com.updapy.model.User;

public interface SettingsService {

	User updateSettings(User user, UpdateSettings newSettings);

	UpdateSettings getSettings(User user);

}
