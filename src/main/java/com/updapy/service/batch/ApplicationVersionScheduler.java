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
	// @Scheduled(fixedDelay = 500000) - fire at start - testing purpose
	public void updateApplicationRepository() {
		log.info("> Applications repository update started");
		List<ApplicationReference> applicationReferences = applicationService.getApplicationReferences();
		for (ApplicationReference applicationReference : applicationReferences) {
			checkNewVersionApplicationReference(applicationReference);
		}
		log.info("< Applications repository updated sucessfully.");
	}

	public void checkNewVersionApplicationReference(ApplicationReference applicationReference) {
		log.info(">> Checking new version of '{}'", applicationReference.getName());

		ApplicationVersion latestApplicationVersion = applicationService.getLatestApplicationVersionNoCache(applicationReference);
		ApplicationVersion latestRemoteApplicationVersion = remoteService.retrieveRemoteLatestVersion(applicationReference);

		if (log.isInfoEnabled()) {
			String currentVersion = (latestApplicationVersion == null) ? "undefined" : latestApplicationVersion.getVersionNumber();
			String remoteVersion = (latestRemoteApplicationVersion == null) ? "undefined" : latestRemoteApplicationVersion.getVersionNumber();
			log.info("Current version: '{}'", currentVersion);
			log.info("Remote version: '{}'", remoteVersion);
		}

		if (latestApplicationVersion == null && latestRemoteApplicationVersion != null) {
			// this is the first time we got a version
			applicationService.addApplicationVersion(latestRemoteApplicationVersion);
		}

		if (latestApplicationVersion != null && latestRemoteApplicationVersion != null) {
			if (latestApplicationVersion.getFormatedVersionNumber().compareTo(latestRemoteApplicationVersion.getFormatedVersionNumber()) == -1) {
				// new version available
				applicationService.addApplicationVersion(latestRemoteApplicationVersion);
			}
		}
	}

}
