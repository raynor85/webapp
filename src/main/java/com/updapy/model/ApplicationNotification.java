package com.updapy.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.BaseEntity;
import com.updapy.model.enumeration.TypeNotification;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "application_notification_seq")
public class ApplicationNotification extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "person_id")
	private User user;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private ApplicationVersion version;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private ApplicationReference application;

	private boolean read;

	@Enumerated(EnumType.STRING)
	private TypeNotification type;

	public ApplicationVersion getVersion() {
		return version;
	}

	public void setVersion(ApplicationVersion version) {
		this.version = version;
	}

	public ApplicationReference getApplication() {
		return application;
	}

	public void setApplication(ApplicationReference application) {
		this.application = application;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public TypeNotification getType() {
		return type;
	}

	public void setType(TypeNotification type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
