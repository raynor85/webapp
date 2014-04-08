package com.updapy.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.BaseEntity;
import com.updapy.model.enumeration.TypeHelpMessage;

@Entity
@SequenceGenerator(allocationSize=1, name="idSequence", sequenceName = "help_message_seq")
public class HelpMessage extends BaseEntity {

	@ManyToOne(optional = false)
	private Person person;
	
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

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
}
