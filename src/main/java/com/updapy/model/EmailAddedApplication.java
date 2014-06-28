package com.updapy.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.EmailBaseEntity;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "email_added_application_seq")
public class EmailAddedApplication extends EmailBaseEntity {

	@ManyToOne(optional = false)
	private ApplicationReference application;

	public ApplicationReference getApplication() {
		return application;
	}

	public void setApplication(ApplicationReference application) {
		this.application = application;
	}

}
