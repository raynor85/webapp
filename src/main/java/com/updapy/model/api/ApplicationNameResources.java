package com.updapy.model.api;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class ApplicationNameResources extends ResourceSupport {

	private List<ApplicationNameResource> applicationNames;

	public List<ApplicationNameResource> getApplicationNames() {
		return applicationNames;
	}

	public void setApplicationNames(List<ApplicationNameResource> applicationNames) {
		this.applicationNames = applicationNames;
	}

}
