package com.updapy.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.updapy.model.common.BaseEntity;

@Entity
@SequenceGenerator(allocationSize=1, name="idSequence", sequenceName = "version_seq")
public class Version extends BaseEntity {

	@ManyToOne(optional = false)
	private ApplicationReference reference;
	
	private String versionNumber;
	
	private String win32Url;
	
	private String win64Url;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date versionDate;

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getWin32Url() {
		return win32Url;
	}

	public void setWin32Url(String win32Url) {
		this.win32Url = win32Url;
	}

	public String getWin64Url() {
		return win64Url;
	}

	public void setWin64Url(String win64Url) {
		this.win64Url = win64Url;
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
	
}
