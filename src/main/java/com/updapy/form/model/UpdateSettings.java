package com.updapy.form.model;

import javax.validation.constraints.NotNull;

import com.updapy.model.enumeration.DashboardGridSize;
import com.updapy.model.enumeration.Lang;
import com.updapy.model.enumeration.OsVersion;

public class UpdateSettings {

	private String name;

	@NotNull
	private Lang langUpdate;

	@NotNull
	private OsVersion osVersion;

	@NotNull
	private boolean emailAlert;

	@NotNull
	private boolean emailEachUpdate;

	@NotNull
	private boolean emailWeekly;

	@NotNull
	private boolean emailAppAdded;

	@NotNull
	private boolean emailNewsletter;

	@NotNull
	private DashboardGridSize dashboardGridSize;

	@NotNull
	private boolean showRating;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEmailAlert() {
		return emailAlert;
	}

	public void setEmailAlert(boolean emailAlert) {
		this.emailAlert = emailAlert;
	}

	public boolean isEmailEachUpdate() {
		return emailEachUpdate;
	}

	public void setEmailEachUpdate(boolean emailEachUpdate) {
		this.emailEachUpdate = emailEachUpdate;
	}

	public boolean isEmailWeekly() {
		return emailWeekly;
	}

	public void setEmailWeekly(boolean emailWeekly) {
		this.emailWeekly = emailWeekly;
	}

	public boolean isEmailAppAdded() {
		return emailAppAdded;
	}

	public void setEmailAppAdded(boolean emailAppAdded) {
		this.emailAppAdded = emailAppAdded;
	}

	public boolean isEmailNewsletter() {
		return emailNewsletter;
	}

	public void setEmailNewsletter(boolean emailNewsletter) {
		this.emailNewsletter = emailNewsletter;
	}

	public Lang getLangUpdate() {
		return langUpdate;
	}

	public void setLangUpdate(Lang langUpdate) {
		this.langUpdate = langUpdate;
	}

	public OsVersion getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(OsVersion osVersion) {
		this.osVersion = osVersion;
	}

	public DashboardGridSize getDashboardGridSize() {
		return dashboardGridSize;
	}

	public void setDashboardGridSize(DashboardGridSize dashboardGridSize) {
		this.dashboardGridSize = dashboardGridSize;
	}

	public boolean getShowRating() {
		return showRating;
	}

	public void setShowRating(boolean showRating) {
		this.showRating = showRating;
	}

}
