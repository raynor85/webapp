package com.updapy.form.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.updapy.model.enumeration.Lang;

public class SendPersonalMessage {

	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	private String message;

	@NotNull
	private Lang langEmail;

	@NotEmpty
	private String title;

	@NotEmpty
	private String subject;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Lang getLangEmail() {
		return langEmail;
	}

	public void setLangEmail(Lang langEmail) {
		this.langEmail = langEmail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
