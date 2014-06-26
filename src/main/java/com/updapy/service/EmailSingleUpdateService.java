package com.updapy.service;

import com.updapy.model.ApplicationVersion;
import com.updapy.model.EmailSingleUpdate;
import com.updapy.model.User;

public interface EmailSingleUpdateService {

	EmailSingleUpdate addEmailSingleUpdate(User user, ApplicationVersion version);

	boolean sendEmailSingleUpdates();

}
