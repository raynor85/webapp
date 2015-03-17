package com.updapy.service;

import org.springframework.transaction.annotation.Transactional;

import com.updapy.model.ApplicationVersion;
import com.updapy.model.EmailSingleUpdate;
import com.updapy.model.User;

@Transactional
public interface EmailSingleUpdateService {

	EmailSingleUpdate addEmailSingleUpdate(User user, ApplicationVersion version);

	int sendEmailSingleUpdates();

}
