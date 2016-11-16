package com.updapy.service.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.SocialException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;

import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;

@Service(value = "facebookService")
public class FacebookServiceImpl extends SocialServiceImpl {

	private Logger log = LoggerFactory.getLogger(FacebookServiceImpl.class);

	@Inject
	private Facebook facebook;

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
			log.info("Sending status '{}' on Facebook", status);
			return;
		}
		try {
			facebook.feedOperations().updateStatus(status);
		} catch (SocialException exception) {
			log.error("An error occured when sending status on Facebook");
		}
	}

}
