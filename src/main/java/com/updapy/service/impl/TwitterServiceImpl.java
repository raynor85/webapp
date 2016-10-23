package com.updapy.service.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.service.TwitterService;
import com.updapy.util.MessageUtils;

@Service
public class TwitterServiceImpl implements TwitterService {

	private static final String SEPARATOR = " - ";
	private static final String PUBLIC_KEY = "cdkieqafegacrjx5dcu63dz7h5fw782oz1cqkwaabku6bpido4";

	private Logger log = LoggerFactory.getLogger(EmailSenderServiceImpl.class);

	@Inject
	private Twitter twitter;

	@Inject
	private MessageUtils messageUtils;

	@Override
	public void sendStatusNewApplication(ApplicationReference application) {
		String url = messageUtils.getSimpleMessage("application.root.url") + "/applications/" + application.getApiName();
		String status = messageUtils.getSimpleMessage("twitter.status.application") + SEPARATOR + application.getName() + SEPARATOR + url;
		sendStatus(status);
	}

	@Override
	public void sendStatusNewVersion(ApplicationVersion version) {
		String url = messageUtils.getSimpleMessage("application.root.url") + "/applications/" + version.getApplication().getApiName() + "/download?key=" + PUBLIC_KEY;
		String status = messageUtils.getSimpleMessage("twitter.status.version") + SEPARATOR + version.getApplication().getName() + " " + version.getVersionNumber() + SEPARATOR + url;
		sendStatus(status);
	}

	private void sendStatus(String status) {
		if (messageUtils.getSimpleMessage("environnement").equals("dev")) {
			// skip status update
			log.info("Sending status '{}' on Twitter", status);
			return;
		}
		Tweet tweet = twitter.timelineOperations().updateStatus(status);
		if (tweet == null) {
			log.error("An error occured when sending status on Twitter");
		}
	}

}
