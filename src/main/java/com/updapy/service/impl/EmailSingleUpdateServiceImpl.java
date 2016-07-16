package com.updapy.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.EmailSingleUpdate;
import com.updapy.model.User;
import com.updapy.repository.EmailSingleUpdateRepository;
import com.updapy.service.EmailSenderService;
import com.updapy.service.EmailSingleUpdateService;

@Service
public class EmailSingleUpdateServiceImpl implements EmailSingleUpdateService {

	@Inject
	private EmailSenderService emailSenderService;
	
	@Inject
	private EmailSingleUpdateRepository emailSingleUpdateRepository;

	@Override
	public EmailSingleUpdate addEmailSingleUpdate(User user, ApplicationVersion version) {
		removeCurrentEmailSingleUpdatesForApplication(user, version.getApplication());
		EmailSingleUpdate emailSingleUpdate = new EmailSingleUpdate();
		emailSingleUpdate.setUser(user);
		emailSingleUpdate.setVersion(version);
		emailSingleUpdate.setSent(false);
		return save(emailSingleUpdate);
	}

	private void removeCurrentEmailSingleUpdatesForApplication(User user, ApplicationReference application) {
		List<EmailSingleUpdate> emailSingleUpdates = getEmailSingleUpdates(user, application);
		if (emailSingleUpdates != null && !emailSingleUpdates.isEmpty()) {
			deleteEmailSingleUpdates(emailSingleUpdates);
		}
	}

	private List<EmailSingleUpdate> getEmailSingleUpdates(User user, ApplicationReference application) {
		return emailSingleUpdateRepository.findByUserAndVersionApplicationAndSentFalse(user, application);
	}

	private void deleteEmailSingleUpdates(List<EmailSingleUpdate> emailSingleUpdates) {
		emailSingleUpdateRepository.delete(emailSingleUpdates);
	}

	@Override
	public int sendEmailSingleUpdates() {
		List<EmailSingleUpdate> emailSingleUpdates = emailSingleUpdateRepository.findBySentFalse(new PageRequest(0, emailSenderService.getNonPriorEmailsMaxSentPerDay()));
		int count = 0;
		for (EmailSingleUpdate emailSingleUpdate : emailSingleUpdates) {
			User user = emailSingleUpdate.getUser();
			boolean hasBeenSent = emailSenderService.sendSingleUpdate(user.getEmail(), user.getRssKey(), emailSingleUpdate.getVersion(), user.getLangEmail());
			if (hasBeenSent) {
				emailSingleUpdate.setSent(true);
				save(emailSingleUpdate);
				count++;
			}
		}
		return count;
	}

	private EmailSingleUpdate save(EmailSingleUpdate emailSingleUpdate) {
		return emailSingleUpdateRepository.saveAndFlush(emailSingleUpdate);
	}

}
