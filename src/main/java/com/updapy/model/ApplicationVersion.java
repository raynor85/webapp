package com.updapy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.updapy.model.common.BaseEntity;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "application_version_seq")
public class ApplicationVersion extends BaseEntity {

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private ApplicationReference application;

	private String versionNumber;

	@Column(columnDefinition = "text")
	private String win32UrlEn;

	@Column(columnDefinition = "text")
	private String win64UrlEn;

	@Column(columnDefinition = "text")
	private String win32UrlFr;

	@Column(columnDefinition = "text")
	private String win64UrlFr;

	@Temporal(TemporalType.TIMESTAMP)
	private Date versionDate;

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getWin32UrlEn() {
		return win32UrlEn;
	}

	public void setWin32UrlEn(String win32UrlEn) {
		this.win32UrlEn = win32UrlEn;
	}

	public String getWin64UrlEn() {
		return win64UrlEn;
	}

	public void setWin64UrlEn(String win64UrlEn) {
		this.win64UrlEn = win64UrlEn;
	}

	public String getWin32UrlFr() {
		return win32UrlFr;
	}

	public void setWin32UrlFr(String win32UrlFr) {
		this.win32UrlFr = win32UrlFr;
	}

	public String getWin64UrlFr() {
		return win64UrlFr;
	}

	public void setWin64UrlFr(String win64UrlFr) {
		this.win64UrlFr = win64UrlFr;
	}

	public Date getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}

	public ApplicationReference getApplication() {
		return application;
	}

	public void setApplication(ApplicationReference application) {
		this.application = application;
	}

	public Version getFormatedVersionNumber() {
		return new Version(versionNumber);
	}

	public boolean isValidVersionNumber() {
		try {
			getFormatedVersionNumber();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return prime * ((application == null) ? 0 : application.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ApplicationVersion other = (ApplicationVersion) obj;
		if (application == null) {
			if (other.application != null)
				return false;
		} else if (!application.equals(other.application))
			return false;
		return true;
	}

}
