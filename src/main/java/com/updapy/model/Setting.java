package com.updapy.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.BaseEntity;
import com.updapy.model.enumeration.Parameter;

@Entity 
@SequenceGenerator(allocationSize=1, name="idSequence", sequenceName="setting_seq")
public class Setting extends BaseEntity {

	@ManyToOne(optional = false)
	private Person person;
	
	@Enumerated(EnumType.STRING)
	private Parameter parameter;
	
	private boolean active;

	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
}
