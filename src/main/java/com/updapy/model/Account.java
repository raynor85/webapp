package com.updapy.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.BaseEntity;

@Entity
@SequenceGenerator(allocationSize=1, name="idSequence", sequenceName = "account_seq")
public class Account extends BaseEntity {

	@OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
	private AccountActivation activation;
	
	@OneToOne(optional = true)
	private AccountRemoval removal;

	public AccountActivation getActivation() {
		return activation;
	}

	public void setActivation(AccountActivation activation) {
		this.activation = activation;
	}

	public AccountRemoval getRemoval() {
		return removal;
	}

	public void setRemoval(AccountRemoval removal) {
		this.removal = removal;
	}

}
