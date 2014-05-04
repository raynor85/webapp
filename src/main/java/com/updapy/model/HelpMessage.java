package com.updapy.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.BaseEntity;
import com.updapy.model.enumeration.TypeHelpMessage;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "help_message_seq")
public class HelpMessage extends BaseEntity {

	@ManyToOne(optional = false)
	@JoinColumn(name = "person_id")
	private User user;

	@Enumerated(EnumType.STRING)
	private TypeHelpMessage type;

	private boolean hidden;

	public TypeHelpMessage getType() {
		return type;
	}

	public void setType(TypeHelpMessage type) {
		this.type = type;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
