package com.updapy.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.EmailBaseEntity;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "email_single_update_seq")
public class EmailSingleUpdate extends EmailBaseEntity {

	@ManyToOne(optional = false)
	private ApplicationVersion version;

	public ApplicationVersion getVersion() {
		return version;
	}

	public void setVersion(ApplicationVersion version) {
		this.version = version;
	}

}
