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
import com.updapy.model.User;
import com.updapy.service.ApplicationService;
import com.updapy.service.EmailAddedApplicationService;
import com.updapy.service.EmailDeletedApplicationService;
import com.updapy.service.EmailSingleUpdateService;
import com.updapy.service.EmailWeeklyUpdateService;
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

	// fire twice a day (noon and midnight)
	@Scheduled(cron = "0 0 0,12 * * *")
	// @Scheduled(fixedDelay = 500000) // fire at start - testing purpose
	public void updateApplicationRepositoryAndCreateEmailSingleUpdates() {
		log.info("> Starting the update of the applications repository (with single emails creation)");
		List<ApplicationReference> applications = applicationService.getApplications();
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
	public void sendEmails() {
		log.info("> Starting to send emails (single)");
		emailSingleUpdateService.sendEmailSingleUpdates();
		log.info("< Single emails sent sucessfully.");

		log.info("> Starting to send emails (weekly)");
		emailWeeklyUpdateService.sendEmailWeeklyUpdates();
		log.info("< Weekly emails sent sucessfully.");

		log.info("> Starting to send emails (application deleted)");
		emailDeletedApplicationService.sendEmailDeletedApplications();
		log.info("< Deleted app emails sent sucessfully.");

		log.info("> Starting to send emails (application added)");
		emailAddedApplicationService.sendEmailAddedApplications();
		log.info("< Added app emails sent sucessfully.");
	}

}
