package com.updapy.service.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;

@Service(value="twitterService")
public class TwitterServiceImpl extends SocialServiceImpl {

	private Logger log = LoggerFactory.getLogger(TwitterServiceImpl.class);

	@Inject
	private Twitter twitter;

	@Override
	public void sendStatusNewApplication(ApplicationReference application) {
		sendStatus(buildStatusNewApplication(application));
	}

	@Override
	public void sendStatusNewVersion(ApplicationVersion version) {
		sendStatus(buildStatusNewVersion(version));
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
