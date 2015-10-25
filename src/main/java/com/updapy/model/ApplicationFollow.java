package com.updapy.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.BaseEntity;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "application_follow_seq")
public class ApplicationFollow extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "person_id")
	private User user;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private ApplicationReference application;

	private boolean emailNotificationActive;

	private Integer rating;

	public ApplicationReference getApplication() {
		return application;
	}

	public void setApplication(ApplicationReference application) {
		this.application = application;
	}

	public boolean isEmailNotificationActive() {
		return emailNotificationActive;
	}

	public void setEmailNotificationActive(boolean emailNotificationActive) {
		this.emailNotificationActive = emailNotificationActive;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

}
