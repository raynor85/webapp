package com.updapy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Person {

	@Id
	@GeneratedValue
	private Integer id;

	private String email;
	
	// Early person = register before the service was available
	private Boolean early;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
