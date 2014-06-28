package com.updapy.service;

import com.updapy.model.ApplicationReference;
import com.updapy.model.EmailDeletedApplication;
import com.updapy.model.User;

public interface EmailDeletedApplicationService {

	EmailDeletedApplication addEmailDeletedApplication(User user, ApplicationReference application);

	boolean sendEmailDeletedApplications();

}
