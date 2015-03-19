package com.updapy.model.stats;

public class FollowedApplication {

	private String applicationName;

	private Long nbFollowers;

	public FollowedApplication(String applicationName, Long nbFollowers) {
		this.applicationName = applicationName;
		this.nbFollowers = nbFollowers;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public Long getNbFollowers() {
		return nbFollowers;
	}

	public void setNbFollowers(Long nbFollowers) {
		this.nbFollowers = nbFollowers;
	}

}
