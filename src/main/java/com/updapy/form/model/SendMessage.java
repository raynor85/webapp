package com.updapy.form.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.updapy.form.model.enumeration.SubjectMessage;

public class SendMessage {

	@NotEmpty
	@Email
	private String email;

	@NotNull
	private SubjectMessage subject;

	@NotEmpty
	private String message;

	private boolean anonymous;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public SubjectMessage getSubject() {
		return subject;
	}

	public void setSubject(SubjectMessage subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isAnonymous() {
		return anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}

}
