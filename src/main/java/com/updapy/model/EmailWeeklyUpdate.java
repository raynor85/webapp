package com.updapy.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.EmailBaseEntity;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "email_weekly_update_seq")
public class EmailWeeklyUpdate extends EmailBaseEntity {

	@ManyToMany(fetch = FetchType.EAGER)
	private List<ApplicationVersion> versions;

	public List<ApplicationVersion> getVersions() {
		return versions;
	}

	public void setVersions(List<ApplicationVersion> versions) {
		this.versions = versions;
	}

}
