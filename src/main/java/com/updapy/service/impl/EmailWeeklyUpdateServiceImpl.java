package com.updapy.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.updapy.model.ApplicationVersion;
import com.updapy.model.EmailWeeklyUpdate;
import com.updapy.model.User;
import com.updapy.repository.EmailWeeklyUpdateRepository;
import com.updapy.service.EmailSenderService;
import com.updapy.service.EmailWeeklyUpdateService;

@Service
public class EmailWeeklyUpdateServiceImpl implements EmailWeeklyUpdateService {

	@Inject
	private EmailSenderService emailSenderService;

	@Inject
	private EmailWeeklyUpdateRepository emailWeeklyUpdateRepository;

	@Override
	public EmailWeeklyUpdate addEmailWeeklyUpdate(User user, List<ApplicationVersion> versions) {
		EmailWeeklyUpdate emailWeeklyUpdate = new EmailWeeklyUpdate();
		emailWeeklyUpdate.setUser(user);
		emailWeeklyUpdate.setVersions(versions);
		emailWeeklyUpdate.setSent(false);
		return save(emailWeeklyUpdate);
	}

	@Override
	public int sendEmailWeeklyUpdates() {
		List<EmailWeeklyUpdate> emailWeeklyUpdates = emailWeeklyUpdateRepository.findBySentFalse(new PageRequest(0, emailSenderService.getNonPriorEmailsMaxSentPerDay()));
		int count = 0;
		for (EmailWeeklyUpdate emailWeeklyUpdate : emailWeeklyUpdates) {
			User user = emailWeeklyUpdate.getUser();
			boolean hasBeenSent = emailSenderService.sendWeeklyUpdates(user.getEmail(), user.getRssKey(), emailWeeklyUpdate.getVersions(), user.getLangEmail());
			if (hasBeenSent) {
				emailWeeklyUpdate.setSent(true);
				save(emailWeeklyUpdate);
				count++;
			}
		}
		return count;
	}

	private EmailWeeklyUpdate save(EmailWeeklyUpdate emailWeeklyUpdate) {
		return emailWeeklyUpdateRepository.saveAndFlush(emailWeeklyUpdate);
	}

}
