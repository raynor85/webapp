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

	// fire twice a day (noon and midnight)
	@Scheduled(cron = "0 0 0,12 * * *")
	//@Scheduled(fixedDelay = 500000) // fire at start - testing purpose
	public void updateApplicationRepositoryAndCreateEmailSingleUpdates() {
		log.info("> Starting the update of the applications repository (with single emails creation)");
		List<ApplicationReference> applications = applicationService.getApplications();
		for (ApplicationReference application : applications) {
			checkNewVersionApplication(application);
		}
		log.info("< Applications repository updated sucessfully.");
	}

	public void checkNewVersionApplication(ApplicationReference application) {
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

	// fire once a week (each Wednesday at 3am)
	@Scheduled(cron = "0 0 3 * * WED")
	//@Scheduled(fixedDelay = 500000) // fire at start - testing purpose
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

	// fire twice a day (4am and 1pm)
	@Scheduled(cron = "0 0 4,13 * * *")
	//@Scheduled(fixedDelay = 500000) // fire at start - testing purpose
	public void sendEmails() {
		log.info("> Starting to send emails (single)");
		emailSingleUpdateService.sendEmailSingleUpdates();
		log.info("< Single emails sent sucessfully.");
		log.info("> Starting to send emails (weekly)");
		emailWeeklyUpdateService.sendEmailWeeklyUpdates();
		log.info("< Weekly emails sent sucessfully.");
	}

}
