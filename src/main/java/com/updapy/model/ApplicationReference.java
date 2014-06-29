package com.updapy.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.BaseEntity;
import com.updapy.model.enumeration.ApplicationCategory;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "application_reference_seq")
public class ApplicationReference extends BaseEntity {

	@Column(unique = true)
	private String name;

	@Column(unique = true)
	private String apiName;

	private String globalUrl;

	private String iconFilename;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "application")
	private List<ApplicationVersion> versions;

	private boolean active;

	private boolean notified;

	@Enumerated(EnumType.STRING)
	private ApplicationCategory category;

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

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public boolean isNotified() {
		return notified;
	}

	public void setNotified(boolean notified) {
		this.notified = notified;
	}

	public ApplicationCategory getCategory() {
		return category;
	}

	public void setCategory(ApplicationCategory category) {
		this.category = category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((apiName == null) ? 0 : apiName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationReference other = (ApplicationReference) obj;
		if (apiName == null) {
			if (other.apiName != null)
				return false;
		} else if (!apiName.equals(other.apiName))
			return false;
		return true;
	}

}
