package com.updapy.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.EmailBaseEntity;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "email_newsletter_seq")
public class EmailNewsletter extends EmailBaseEntity {

	@ManyToOne(optional = false)
	private Newsletter newsletter;

	public Newsletter getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(Newsletter newsletter) {
		this.newsletter = newsletter;
	}

}
