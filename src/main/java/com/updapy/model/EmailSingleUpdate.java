package com.updapy.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.BaseEntity;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "email_single_update_seq")
public class EmailSingleUpdate extends BaseEntity {

	@ManyToOne(optional = false)
	@JoinColumn(name = "person_id")
	private User user;

	@ManyToOne(optional = false)
	private ApplicationVersion version;

	private boolean sent;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ApplicationVersion getVersion() {
		return version;
	}

	public void setVersion(ApplicationVersion version) {
		this.version = version;
	}

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

}
