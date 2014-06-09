package com.updapy.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.BaseEntity;
import com.updapy.model.enumeration.TypeNofication;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "application_notification_seq")
public class ApplicationNotification extends BaseEntity {

	@ManyToOne(optional = false)
	@JoinColumn(name = "person_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private ApplicationReference referenceApp;

	private boolean read;

	@Enumerated(EnumType.STRING)
	private TypeNofication type;

	public ApplicationReference getReferenceApp() {
		return referenceApp;
	}

	public void setReferenceApp(ApplicationReference referenceApp) {
		this.referenceApp = referenceApp;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public TypeNofication getType() {
		return type;
	}

	public void setType(TypeNofication type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
