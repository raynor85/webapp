package com.updapy.model.stats;

public class Follower {

	private String name;

	private String email;

	private Long nbApplications;

	public Follower(String name, String email, Long nbApplications) {
		this.name = name;
		this.email = email;
		this.nbApplications = nbApplications;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getNbApplications() {
		return nbApplications;
	}

	public void setNbApplications(Long nbApplications) {
		this.nbApplications = nbApplications;
	}

}
