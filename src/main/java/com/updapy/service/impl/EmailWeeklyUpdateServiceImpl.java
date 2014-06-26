package com.updapy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.updapy.form.model.NewVersion;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.EmailWeeklyUpdate;
import com.updapy.model.User;
import com.updapy.repository.EmailWeeklyUpdateRepository;
import com.updapy.service.EmailSenderService;
import com.updapy.service.EmailWeeklyUpdateService;
import com.updapy.service.UserService;

@Service
public class EmailWeeklyUpdateServiceImpl implements EmailWeeklyUpdateService {

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private UserService userService;

	@Autowired
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
	public boolean sendEmailWeeklyUpdates() {
		List<EmailWeeklyUpdate> emailWeeklyUpdates = emailWeeklyUpdateRepository.findBySentFalse(new PageRequest(0, emailSenderService.getNonPriorEmailsMaxSentPerDay()));
		for (EmailWeeklyUpdate emailWeeklyUpdate : emailWeeklyUpdates) {
			User user = emailWeeklyUpdate.getUser();
			List<NewVersion> newVersions = new ArrayList<NewVersion>();
			for (ApplicationVersion newVersion : emailWeeklyUpdate.getVersions()) {
				newVersions.add(new NewVersion(newVersion.getApplication().getName(), newVersion.getVersionNumber(), userService.getDownloadUrlMatchingSettings(user, newVersion)));
			}
			boolean hasBeenSent = emailSenderService.sendWeeklyUpdates(user.getEmail(), newVersions, user.getLangEmail());
			if (hasBeenSent) {
				emailWeeklyUpdate.setSent(true);
				save(emailWeeklyUpdate);
			}
		}
		return true;
	}

	private EmailWeeklyUpdate save(EmailWeeklyUpdate emailWeeklyUpdate) {
		return emailWeeklyUpdateRepository.saveAndFlush(emailWeeklyUpdate);
	}

}
