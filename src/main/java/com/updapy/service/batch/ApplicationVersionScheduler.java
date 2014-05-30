package com.updapy.service.batch;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.updapy.service.MailSenderService;

@Component
public class ApplicationVersionScheduler {

	@Autowired
	private MailSenderService mailSenderService;

	// Fire every hours
	@Scheduled(cron = "0 0 * * * *")
	public void updateApplicationVersion() {
		mailSenderService.sendActivationLink("test@updapy.com", "test" + new Date());
	}

}
