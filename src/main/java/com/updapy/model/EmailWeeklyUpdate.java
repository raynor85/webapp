package com.updapy.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.BaseEntity;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "email_weekly_update_seq")
public class EmailWeeklyUpdate extends BaseEntity {

	@ManyToOne(optional = false)
	@JoinColumn(name = "person_id")
	private User user;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<ApplicationVersion> versions;

	private boolean sent;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ApplicationVersion> getVersions() {
		return versions;
	}

	public void setVersions(List<ApplicationVersion> versions) {
		this.versions = versions;
	}

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

}
