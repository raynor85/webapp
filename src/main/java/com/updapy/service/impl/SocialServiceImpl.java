package com.updapy.service.impl;

import javax.inject.Inject;

import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.service.SocialService;
import com.updapy.util.MessageUtils;

public abstract class SocialServiceImpl implements SocialService {

	private static final String SEPARATOR = " - ";
	private static final String PUBLIC_KEY = "cdkieqafegacrjx5dcu63dz7h5fw782oz1cqkwaabku6bpido4";

	@Inject
	protected MessageUtils messageUtils;

	protected String buildStatusNewApplication(ApplicationReference application) {
		return messageUtils.getSimpleMessage("twitter.status.application") + SEPARATOR + application.getName() + SEPARATOR + getUrl(application);
	}

	protected String buildStatusNewVersion(ApplicationVersion version) {
		return messageUtils.getSimpleMessage("twitter.status.version") + SEPARATOR + version.getApplication().getName() + " " + version.getVersionNumber() + SEPARATOR + getUrl(version);
	}

	private String getUrl(ApplicationReference application) {
		return messageUtils.getSimpleMessage("application.root.url") + "/applications/" + application.getApiName();
	}

	private String getUrl(ApplicationVersion version) {
		return messageUtils.getSimpleMessage("application.root.url") + "/applications/" + version.getApplication().getApiName() + "/download?key=" + PUBLIC_KEY;
	}
}
