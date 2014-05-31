package com.updapy.service.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.repository.ApplicationReferenceRepository;
import com.updapy.repository.ApplicationVersionRepository;
import com.updapy.service.RemoteService;

@Component
public class ApplicationVersionScheduler {

	Logger log = LoggerFactory.getLogger(ApplicationVersionScheduler.class);

	@Autowired
	ApplicationReferenceRepository applicationReferenceRepository;

	@Autowired
	ApplicationVersionRepository applicationVersionRepository;

	@Autowired
	RemoteService remoteService;

	// fire every hour
	// @Scheduled(cron = "0 0 * * * *")
	//@Scheduled(fixedDelay = 2000000) // fire at start - testing purpose
	public void updateApplicationRepository() {
		log.info("> Applications repository update started");
		List<ApplicationReference> applicationReferences = applicationReferenceRepository.findAll();
		for (ApplicationReference applicationReference : applicationReferences) {
			checkNewVersionApplicationReference(applicationReference);
		}
		log.info("< Applications repository updated sucessfully.");
	}

	private void checkNewVersionApplicationReference(ApplicationReference applicationReference) {
		log.info(">> Checking new version of '{}'", applicationReference.getName());

		ApplicationVersion latestApplicationVersion = applicationVersionRepository.findLatestByApplicationReference(applicationReference);
		ApplicationVersion latestRemoteApplicationVersion = remoteService.retrieveRemoteLatestVersion(applicationReference);

		if (log.isInfoEnabled()) {
			String currentVersion = (latestApplicationVersion == null) ? "undefined" : latestApplicationVersion.getVersionNumber();
			String remoteVersion = (latestRemoteApplicationVersion == null) ? "undefined" : latestRemoteApplicationVersion.getVersionNumber();
			log.info("Current version: '{}'", currentVersion);
			log.info("Remote version: '{}'", remoteVersion);
		}

		if (latestApplicationVersion == null && latestRemoteApplicationVersion != null) {
			// this is the first time we got a version
			addApplicationVersion(latestRemoteApplicationVersion);
		}

		if (latestApplicationVersion != null && latestRemoteApplicationVersion != null) {
			if (latestApplicationVersion.getFormatedVersionNumber().compareTo(latestRemoteApplicationVersion.getFormatedVersionNumber()) == -1) {
				// new version available
				addApplicationVersion(latestRemoteApplicationVersion);
			}
		}
	}

	private void addApplicationVersion(ApplicationVersion latestRemoteApplicationVersion) {
		applicationVersionRepository.saveAndFlush(latestRemoteApplicationVersion);
		log.info("New version added successfully.");
	}

}
