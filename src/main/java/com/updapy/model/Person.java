package com.updapy.model;

import javax.persistence.Entity;

@Entity
public class Person extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String email;
	
	// Early person = register before the service was available
	private Boolean early;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEarly() {
		return early;
	}

	public void setEarly(Boolean early) {
		this.early = early;
	}

}
