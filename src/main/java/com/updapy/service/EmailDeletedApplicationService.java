package com.updapy.service;

import org.springframework.transaction.annotation.Transactional;

import com.updapy.model.ApplicationReference;
import com.updapy.model.EmailDeletedApplication;
import com.updapy.model.User;

@Transactional
public interface EmailDeletedApplicationService {

	EmailDeletedApplication addEmailDeletedApplication(User user, ApplicationReference application);

	int sendEmailDeletedApplications();

}
