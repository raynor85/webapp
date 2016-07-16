package com.updapy.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.updapy.model.ApplicationReference;
import com.updapy.model.EmailAddedApplication;
import com.updapy.model.User;
import com.updapy.repository.EmailAddedApplicationRepository;
import com.updapy.service.EmailAddedApplicationService;
import com.updapy.service.EmailSenderService;

@Service
public class EmailAddedApplicationServiceImpl implements EmailAddedApplicationService {

	@Inject
	private EmailSenderService emailSenderService;

	@Inject
	private EmailAddedApplicationRepository emailAddedApplicationRepository;

	@Override
	public EmailAddedApplication addEmailAddedApplication(User user, List<ApplicationReference> applications) {
		EmailAddedApplication emailAddedApplication = new EmailAddedApplication();
		emailAddedApplication.setUser(user);
		emailAddedApplication.setApplications(applications);
		emailAddedApplication.setSent(false);
		return save(emailAddedApplication);
	}

	@Override
	public int sendEmailAddedApplications() {
		List<EmailAddedApplication> emailAddedApplications = emailAddedApplicationRepository.findBySentFalse(new PageRequest(0, emailSenderService.getNonPriorEmailsMaxSentPerDay()));
		int count = 0;
		for (EmailAddedApplication emailAddedApplication : emailAddedApplications) {
			User user = emailAddedApplication.getUser();
			boolean hasBeenSent = emailSenderService.sendAddedApplication(user.getEmail(), emailAddedApplication.getApplications(), user.getLangEmail());
			if (hasBeenSent) {
				emailAddedApplication.setSent(true);
				save(emailAddedApplication);
				count++;
			}
		}
		return count;
	}

	private EmailAddedApplication save(EmailAddedApplication emailAddedApplication) {
		return emailAddedApplicationRepository.saveAndFlush(emailAddedApplication);
	}

}
