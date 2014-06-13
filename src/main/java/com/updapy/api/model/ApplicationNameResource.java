package com.updapy.api.model;

import org.springframework.hateoas.ResourceSupport;

public class ApplicationNameResource extends ResourceSupport {

	private String realName;

	private String apiName;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

}
