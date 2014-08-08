package com.updapy.form.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class ChangeEmailUser {

	@NotEmpty
	@Email
	private String newEmail;

	@NotEmpty
	private String repeatNewEmail;

	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

	public String getRepeatNewEmail() {
		return repeatNewEmail;
	}

	public void setRepeatNewEmail(String repeatNewEmail) {
		this.repeatNewEmail = repeatNewEmail;
	}

}
