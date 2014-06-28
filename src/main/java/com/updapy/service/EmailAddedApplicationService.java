package com.updapy.service;

import com.updapy.model.ApplicationReference;
import com.updapy.model.EmailAddedApplication;
import com.updapy.model.User;

public interface EmailAddedApplicationService {

	EmailAddedApplication addEmailAddedApplication(User user, ApplicationReference application);

	boolean sendEmailAddedApplications();

}
