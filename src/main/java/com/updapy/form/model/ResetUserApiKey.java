package com.updapy.form.model;

import org.hibernate.validator.constraints.NotEmpty;

public class ResetUserApiKey {

	@NotEmpty
	private String apiKey;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

}
