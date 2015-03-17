package com.updapy.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.updapy.model.ApplicationReference;
import com.updapy.model.EmailAddedApplication;
import com.updapy.model.User;

@Transactional
public interface EmailAddedApplicationService {

	EmailAddedApplication addEmailAddedApplication(User user, List<ApplicationReference> applications);

	int sendEmailAddedApplications();

}
