package com.updapy.service.scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.Newsletter;
import com.updapy.model.User;
import com.updapy.model.enumeration.TypeRetrievalError;
import com.updapy.service.ApplicationService;
import com.updapy.service.EmailAddedApplicationService;
import com.updapy.service.EmailDeletedApplicationService;
import com.updapy.service.EmailSingleUpdateService;
import com.updapy.service.EmailWeeklyUpdateService;
import com.updapy.service.NewsletterService;
import com.updapy.service.RemoteService;
import com.updapy.service.RetrievalErrorService;
import com.updapy.service.SettingsService;
import com.updapy.service.SocialService;
import com.updapy.service.UserService;
import com.updapy.service.impl.RemoteServiceImpl;

@Service
public class ApplicationVersionScheduler {

	private Logger log = LoggerFactory.getLogger(ApplicationVersionScheduler.class);

	@Inject
	private ApplicationService applicationService;

	@Inject
	private SocialService twitterService;

	@Inject
	private SocialService facebookService;

	@Inject
	private UserService userService;

	@Inject
	private SettingsService settingsService;

	@Inject
	private RemoteService remoteService;

	@Inject
	private EmailSingleUpdateService emailSingleUpdateService;

	@Inject
	private EmailWeeklyUpdateService emailWeeklyUpdateService;

	@Inject
	private EmailDeletedApplicationService emailDeletedApplicationService;

	@Inject
	private EmailAddedApplicationService emailAddedApplicationService;

	@Inject
	private NewsletterService newsletterService;

	@Inject
	private RetrievalErrorService retrievalErrorService;

	// fire every 3 hours
	@Scheduled(cron = "0 0 */3 * * *")
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
			log.info("Remote version : '{}'", remoteVersion);
		}

		if (latestVersion == null && latestRemoteVersion != null) {
			// this is the first time we got a version
			applicationService.addVersion(latestRemoteVersion);
		}

		if (latestVersion != null && latestRemoteVersion != null) {
			int comparisonResult = latestVersion.getFormatedVersionNumber().compareTo(latestRemoteVersion.getFormatedVersionNumber());
			if (comparisonResult == -1) {
				// new version available
				applicationService.addVersion(latestRemoteVersion);
				twitterService.sendStatusNewVersion(latestRemoteVersion);
				facebookService.sendStatusNewVersion(latestRemoteVersion);
			} else if (comparisonResult == 0) {
				if (!latestRemoteVersion.getWin32UrlEn().equalsIgnoreCase(latestVersion.getWin32UrlEn())) {
					// the version is the same but the download URL has changed
					retrievalErrorService.addRetrievalError(application, TypeRetrievalError.SAME_VERSION_DIFFERENT_URL, "Got remote URL '" + latestRemoteVersion.getWin32UrlEn() + "' but current URL is '" + latestVersion.getWin32UrlEn() + "'");
				} else {
					retrievalErrorService.deleteRetrievalErrors(application, Arrays.asList(TypeRetrievalError.SAME_VERSION_DIFFERENT_URL));
				}
			} else if (comparisonResult == 1 && !latestRemoteVersion.getVersionNumber().equals(RemoteServiceImpl.VERSION_NOT_FOUND)) {
				// the remote version has a smaller number
				retrievalErrorService.addRetrievalError(application, TypeRetrievalError.REMOTE_NEW_VERSION_WITH_NUMBER_NOT_CONSISTENT, "Got remote version '" + latestRemoteVersion.getVersionNumber() + "' but current version is '" + latestVersion.getVersionNumber() + "'");
			}
		}
	}

	// fire once a week (each Wednesday at 1am)
	@Scheduled(cron = "0 0 1 * * WED")
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
	@Transactional
	public void createEmailsAndNotificationsForAddedApplications() {
		log.info("> Starting creating emails and notifications for applications added");
		List<ApplicationReference> addedApplications = applicationService.getAddedApplications();
		if (!addedApplications.isEmpty()) {
			List<User> userToNotifys = userService.findUsersActive();
			createEmailsForAddedApplication(userToNotifys, addedApplications);
			for (ApplicationReference addedApplication : addedApplications) {
				notifyUsersForAddedApplication(userToNotifys, addedApplication);
				applicationService.markAsNotified(addedApplication);
				twitterService.sendStatusNewApplication(addedApplication);
				facebookService.sendStatusNewApplication(addedApplication);
			}
		}
		log.info("< Emails and notifications for added app created sucessfully.");
	}

	private void createEmailsForAddedApplication(List<User> users, List<ApplicationReference> addedApplications) {
		for (User user : users) {
			if (settingsService.isEmailAppAddedActive(user)) {
				emailAddedApplicationService.addEmailAddedApplication(user, addedApplications);
			}
		}
	}

	private void notifyUsersForAddedApplication(List<User> users, ApplicationReference addedApplication) {
		for (User user : users) {
			userService.notifyForAddedApplication(user, addedApplication);
		}
	}

	// fire twice a day (3am and 3pm)
	@Scheduled(cron = "0 0 3,15 * * *")
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

	// fire one a day (5pm)
	@Scheduled(cron = "0 0 17 * * *")
	@Transactional
	public void checkThatDownloadLinksAreValid() {
		log.info("> Starting download links validity");
		List<ApplicationReference> applications = applicationService.getApplications();
		for (ApplicationReference application : applications) {
			ApplicationVersion version = applicationService.getLatestVersion(application);
			if (version != null && (!remoteService.isUrlValid(version.getWin32UrlEn()) || !remoteService.isUrlValid(version.getWin32UrlFr()) || !remoteService.isUrlValid(version.getWin64UrlEn()) || !remoteService.isUrlValid(version.getWin64UrlFr()))) {
				log.info("There is a download link invalid for application: " + application.getName());
				retrievalErrorService.addRetrievalError(application, TypeRetrievalError.LOCAL_URL_VERSION_ERROR);
			} else {
				retrievalErrorService.deleteRetrievalErrors(application, Arrays.asList(TypeRetrievalError.LOCAL_URL_VERSION_ERROR));
			}
		}
		log.info("< Download links validated sucessfully.");
	}

	// fire twice a day (5am and 5pm)
	@Scheduled(cron = "0 0 5,17 * * *")
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

		count = retrievalErrorService.sendEmailRetrievalErrors();
		log.info(count + " emails for error sent.");

		log.info("< All emails have been sent for today.");
	}

}
