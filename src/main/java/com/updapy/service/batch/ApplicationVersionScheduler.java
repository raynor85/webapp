package com.updapy.service.batch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ApplicationVersionScheduler {

	// Fire every hour
	@Scheduled(cron = "0 0 * * * *")
	public void updateApplicationVersion() {
		// TODO: Check if new version are available for each application reference
	}

}
