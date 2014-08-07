package com.updapy.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.EmailBaseEntity;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "email_added_application_seq")
public class EmailAddedApplication extends EmailBaseEntity {

	@ManyToMany(fetch = FetchType.EAGER)
	private List<ApplicationReference> applications;

	public List<ApplicationReference> getApplications() {
		return applications;
	}

	public void setApplications(List<ApplicationReference> applications) {
		this.applications = applications;
	}

}
