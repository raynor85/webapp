package com.updapy.service;

import java.util.List;

import com.updapy.model.ApplicationReference;
import com.updapy.model.EmailAddedApplication;
import com.updapy.model.User;

public interface EmailAddedApplicationService {

	EmailAddedApplication addEmailAddedApplication(User user, List<ApplicationReference> applications);

	int sendEmailAddedApplications();

}
