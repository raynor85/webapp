package com.updapy.service.batch;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.Newsletter;
import com.updapy.model.User;
import com.updapy.service.ApplicationService;
import com.updapy.service.EmailAddedApplicationService;
import com.updapy.service.EmailDeletedApplicationService;
import com.updapy.service.EmailSingleUpdateService;
import com.updapy.service.EmailWeeklyUpdateService;
import com.updapy.service.NewsletterService;
import com.updapy.service.RemoteService;
import com.updapy.service.SettingsService;
import com.updapy.service.UserService;

@Component
public class ApplicationVersionScheduler {

	private Logger log = LoggerFactory.getLogger(ApplicationVersionScheduler.class);

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private UserService userService;

	@Autowired
	private SettingsService settingsService;

	@Autowired
	private RemoteService remoteService;

	@Autowired
	private EmailSingleUpdateService emailSingleUpdateService;

	@Autowired
	private EmailWeeklyUpdateService emailWeeklyUpdateService;

	@Autowired
	private EmailDeletedApplicationService emailDeletedApplicationService;

	@Autowired
	private EmailAddedApplicationService emailAddedApplicationService;

	@Autowired
	private NewsletterService newsletterService;

	// fire twice a day (noon and midnight)
	@Scheduled(cron = "0 0 0,12 * * *")
	// @Scheduled(fixedDelay = 500000) // fire at start - testing purpose
	public void updateApplicationRepositoryAndCreateEmailSingleUpdates() {
		log.info("> Starting the update of the applications repository (with single emails creation)");
		List<ApplicationReference> applications = applicationService.getAllActiveApplications();
		for (ApplicationReference application : applications) {
			checkNewVersionApplication(application);
		}
		log.info("< Applications repository updated sucessfully.");
	}

	private void checkNewVersionApplication(ApplicationReference application) {
		log.info(">> Checking new version of '{}'", application.getName());

		ApplicationVersion latestVersion = applicationService.getLatestVersionNoCache(application);
		ApplicationVersion latestRemoteVersion = remoteService.retrieveRemoteLatestVersion(application);

		if (log.isInfoEnabled()) {
			String currentVersion = (latestVersion == null) ? "undefined" : latestVersion.getVersionNumber();
			String remoteVersion = (latestRemoteVersion == null) ? "undefined" : latestRemoteVersion.getVersionNumber();
			log.info("Current version: '{}'", currentVersion);
			log.info("Remote version: '{}'", remoteVersion);
		}

		if (latestVersion == null && latestRemoteVersion != null) {
			// this is the first time we got a version
			applicationService.addVersion(latestRemoteVersion);
		}

		if (latestVersion != null && latestRemoteVersion != null) {
			if (latestVersion.getFormatedVersionNumber().compareTo(latestRemoteVersion.getFormatedVersionNumber()) == -1) {
				// new version available
				applicationService.addVersion(latestRemoteVersion);
			}
		}
	}

	// fire once a week (each Wednesday at 1am)
	@Scheduled(cron = "0 0 1 * * WED")
	// @Scheduled(fixedDelay = 500000) // fire at start - testing purpose
	@Transactional
	public void createEmailWeeklyUpdates() {
		DateTime now = new LocalDate().toDateTimeAtCurrentTime();
		Date from = now.minusDays(7).toDate();
		Date to = now.toDate();

		log.info("> Starting creating emails (weekly), gathering new versions between '{}' and '{}'", from, to);

		Map<User, List<ApplicationVersion>> versionsPerUsers = new HashMap<User, List<ApplicationVersion>>();

		List<ApplicationVersion> versions = applicationService.getNewVersionsOnPeriod(from, to);
		for (ApplicationVersion version : versions) {
			List<User> users = userService.findUsersFollowingApplication(version.getApplication());
			for (User user : users) {
				if (settingsService.isEmailWeeklyActive(user)) {
					if (versionsPerUsers.containsKey(user)) {
						versionsPerUsers.get(user).add(version);
					} else {
						List<ApplicationVersion> userVersions = new ArrayList<ApplicationVersion>();
						userVersions.add(version);
						versionsPerUsers.put(user, userVersions);
					}
				}
			}
		}

		for (Entry<User, List<ApplicationVersion>> versionsPerUser : versionsPerUsers.entrySet()) {
			emailWeeklyUpdateService.addEmailWeeklyUpdate(versionsPerUser.getKey(), versionsPerUser.getValue());
		}

		log.info("< Weekly emails created sucessfully.");
	}

	// fire twice a day (2am and 2pm)
	@Scheduled(cron = "0 0 2,14 * * *")
	// @Scheduled(fixedDelay = 500000) // fire at start - testing purpose
	@Transactional
	public void createEmailsAndNotificationsForAddedApplications() {
		log.info("> Starting creating emails and notifications for applications added");
		List<ApplicationReference> addedApplications = applicationService.getAddedApplications();
		for (ApplicationReference addedApplication : addedApplications) {
			notifyUsersForAddedApplication(addedApplication);
			applicationService.markAsNotified(addedApplication);
		}
		log.info("< Emails and notifications for added app created sucessfully.");
	}

	private void notifyUsersForAddedApplication(ApplicationReference addedApplication) {
		List<User> userToNotifys = userService.findUsersActive();
		for (User userToNotify : userToNotifys) {
			userService.notifyForAddedApplication(userToNotify, addedApplication);
		}
	}

	// fire twice a day (3am and 3pm)
	@Scheduled(cron = "0 0 3,15 * * *")
	// @Scheduled(fixedDelay = 500000) // fire at start - testing purpose
	@Transactional
	public void createEmailsAndNotificationsForDeletedApplications() {
		log.info("> Starting creating emails and notifications for applications deleted");
		List<ApplicationReference> deletedApplications = applicationService.getDeletedApplications();
		for (ApplicationReference deletedApplication : deletedApplications) {
			notifyUsersForDeletedApplication(deletedApplication);
			applicationService.markAsNotified(deletedApplication);
		}
		log.info("< Emails and notifications for deleted app created sucessfully.");
	}

	private void notifyUsersForDeletedApplication(ApplicationReference deletedApplication) {
		List<User> userToNotifys = userService.findUsersFollowingApplication(deletedApplication);
		for (User userToNotify : userToNotifys) {
			userService.notifyForDeletedApplication(userToNotify, deletedApplication);
		}
	}

	// fire twice a day (4am and 4pm)
	@Scheduled(cron = "0 0 4,16 * * *")
	// @Scheduled(fixedDelay = 500000) // fire at start - testing purpose
	@Transactional
	public void createEmailsNewsletters() {
		log.info("> Starting creating emails for newsletters");
		List<Newsletter> newsletters = newsletterService.getNewsletters();
		for (Newsletter newsletter : newsletters) {
			notifyUsersForNewsletter(newsletter);
			newsletterService.markAsNotified(newsletter);
		}
		log.info("< Emails for newsletters created sucessfully.");
	}

	private void notifyUsersForNewsletter(Newsletter newsletter) {
		List<User> userToNotifys = userService.findUsersFollowingNewsletters();
		for (User userToNotify : userToNotifys) {
			newsletterService.addEmailNewsletter(userToNotify, newsletter);
		}
	}

	// fire twice a day (5am and 5pm)
	@Scheduled(cron = "0 0 5,17 * * *")
	// @Scheduled(fixedDelay = 500000) // fire at start - testing purpose
	public void sendEmails() {
		log.info("> Starting to send emails");

		int count = emailSingleUpdateService.sendEmailSingleUpdates();
		log.info(count + " emails for single update sent.");

		count = emailWeeklyUpdateService.sendEmailWeeklyUpdates();
		log.info(count + " emails for weekly update sent.");

		count = emailDeletedApplicationService.sendEmailDeletedApplications();
		log.info(count + " emails for deleted app sent.");

		count = emailAddedApplicationService.sendEmailAddedApplications();
		log.info(count + " emails for added app sent.");

		count = newsletterService.sendEmailNewsletters();
		log.info(count + " newsletters sent.");

		log.info("< All emails have been sent for today.");
	}

}
