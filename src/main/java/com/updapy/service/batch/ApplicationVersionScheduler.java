package com.updapy.service.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.service.ApplicationService;
import com.updapy.service.RemoteService;

@Component
public class ApplicationVersionScheduler {

	private Logger log = LoggerFactory.getLogger(ApplicationVersionScheduler.class);

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private RemoteService remoteService;

	// fire twice a day
	@Scheduled(cron = "0 0 0,12 * * *")
	//@Scheduled(fixedDelay = 500000) // fire at start - testing purpose
	public void updateApplicationRepository() {
		log.info("> Applications repository update started");
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

}
