package com.updapy.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.BaseEntity;

@Entity
@SequenceGenerator(allocationSize=1, name="idSequence", sequenceName = "application_follow_seq")
public class ApplicationFollow extends BaseEntity {

	@ManyToOne(optional = false)
	private Person person;
	
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	private ApplicationReference referenceApp;
	
	private boolean emailNotificationActive;

	public ApplicationReference getReferenceApp() {
		return referenceApp;
	}

	public void setReferenceApp(ApplicationReference referenceApp) {
		this.referenceApp = referenceApp;
	}

	public boolean isEmailNotificationActive() {
		return emailNotificationActive;
	}

	public void setEmailNotificationActive(boolean emailNotificationActive) {
		this.emailNotificationActive = emailNotificationActive;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
}