package com.updapy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.updapy.model.ApplicationReference;
import com.updapy.model.EmailDeletedApplication;
import com.updapy.model.User;
import com.updapy.repository.EmailDeletedApplicationRepository;
import com.updapy.service.EmailDeletedApplicationService;
import com.updapy.service.EmailSenderService;
import com.updapy.service.UserService;

@Service
public class EmailDeletedApplicationServiceImpl implements EmailDeletedApplicationService {

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailDeletedApplicationRepository emailDeletedApplicationRepository;

	@Override
	public EmailDeletedApplication addEmailDeletedApplication(User user, ApplicationReference application) {
		EmailDeletedApplication emailDeletedApplication = new EmailDeletedApplication();
		emailDeletedApplication.setUser(user);
		emailDeletedApplication.setApplication(application);
		emailDeletedApplication.setSent(false);
		return save(emailDeletedApplication);
	}

	@Override
	public int sendEmailDeletedApplications() {
		List<EmailDeletedApplication> emailDeletedApplications = emailDeletedApplicationRepository.findBySentFalse(new PageRequest(0, emailSenderService.getNonPriorEmailsMaxSentPerDay()));
		int count = 0;
		for (EmailDeletedApplication emailDeletedApplication : emailDeletedApplications) {
			User user = emailDeletedApplication.getUser();
			boolean hasBeenSent = emailSenderService.sendDeletedApplication(user.getEmail(), emailDeletedApplication.getApplication(), user.getLangEmail());
			if (hasBeenSent) {
				emailDeletedApplication.setSent(true);
				save(emailDeletedApplication);
				count++;
			}
		}
		return count;
	}

	private EmailDeletedApplication save(EmailDeletedApplication emailDeletedApplication) {
		return emailDeletedApplicationRepository.saveAndFlush(emailDeletedApplication);
	}

}
