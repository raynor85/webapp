package com.updapy.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.updapy.model.common.BaseEntity;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "application_version_seq")
public class ApplicationVersion extends BaseEntity {

	@ManyToOne(optional = false)
	private ApplicationReference reference;

	private String versionNumber;

	private String win32UrlEn;

	private String win64UrlEn;

	private String win32UrlFr;

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

	public ApplicationReference getReference() {
		return reference;
	}

	public void setReference(ApplicationReference reference) {
		this.reference = reference;
	}

	public Version getFormatedVersionNumber() {
		return new Version(versionNumber);
	}

}
