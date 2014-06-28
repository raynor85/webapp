package com.updapy.model.common;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.updapy.model.User;

@MappedSuperclass
public class EmailBaseEntity extends BaseEntity {

	@ManyToOne(optional = false)
	@JoinColumn(name = "person_id")
	private User user;

	private boolean sent;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

}
