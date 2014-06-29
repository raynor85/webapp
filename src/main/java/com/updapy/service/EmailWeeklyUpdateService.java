package com.updapy.service;

import java.util.List;

import com.updapy.model.ApplicationVersion;
import com.updapy.model.EmailWeeklyUpdate;
import com.updapy.model.User;

public interface EmailWeeklyUpdateService {

	EmailWeeklyUpdate addEmailWeeklyUpdate(User user, List<ApplicationVersion> versions);

	int sendEmailWeeklyUpdates();

}
