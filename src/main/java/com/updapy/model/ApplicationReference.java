package com.updapy.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.BaseEntity;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "application_reference_seq")
public class ApplicationReference extends BaseEntity {

	@Column(unique = true)
	private String name;

	private String globalUrl;

	private String iconFilename;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "reference")
	private List<ApplicationVersion> versions;

	private boolean active;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGlobalUrl() {
		return globalUrl;
	}

	public void setGlobalUrl(String globalUrl) {
		this.globalUrl = globalUrl;
	}

	public String getIconFilename() {
		return iconFilename;
	}

	public void setIconFilename(String iconFilename) {
		this.iconFilename = iconFilename;
	}

	public List<ApplicationVersion> getVersions() {
		return versions;
	}

	public void setVersions(List<ApplicationVersion> versions) {
		this.versions = versions;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
