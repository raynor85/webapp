package com.updapy.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.BaseEntity;

@Entity
@SequenceGenerator(allocationSize=1, name="idSequence", sequenceName = "application_request_seq")
public class ApplicationRequest extends BaseEntity {

	@ManyToOne(optional = false)
	private Person person;
	
	private String name;
	
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
