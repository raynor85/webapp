package com.updapy.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.updapy.model.common.BaseEntity;

@Entity
@SequenceGenerator(allocationSize=1, name="idSequence", sequenceName = "account_activation_seq")
public class AccountActivation extends BaseEntity {

	private boolean active;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date generationKeyDate;
	
	private String key;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date activationDate;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getGenerationKeyDate() {
		return generationKeyDate;
	}

	public void setGenerationKeyDate(Date generationKeyDate) {
		this.generationKeyDate = generationKeyDate;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

}
