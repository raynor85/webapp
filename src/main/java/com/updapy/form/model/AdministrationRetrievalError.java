package com.updapy.form.model;

import com.updapy.model.ApplicationVersion;
import com.updapy.model.RetrievalError;

public class AdministrationRetrievalError {

	private RetrievalError retrievalError;

	private ApplicationVersion latestVersion;

	public RetrievalError getRetrievalError() {
		return retrievalError;
	}

	public void setRetrievalError(RetrievalError retrievalError) {
		this.retrievalError = retrievalError;
	}

	public ApplicationVersion getLatestVersion() {
		return latestVersion;
	}

	public void setLatestVersion(ApplicationVersion latestVersion) {
		this.latestVersion = latestVersion;
	}

}
