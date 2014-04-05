package com.updapy.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Version
	private Long version;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	public Long getId() {
		return id;
	}

	public Long getVersion() {
		return version;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	/** 
	 * Sets creationDate before insert
	 */
	@PrePersist
	public void setCreationDate() {
		this.creationDate = new Date();
	}  
  
	/**
	 * Sets updateDate before update
	 */
	@PreUpdate
	public void setUpdateDate() {
		this.updateDate = new Date();
	} 
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (this.getId() != null ? this.getId().hashCode() : 0);

		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null)
			return false;
		if (getClass() != object.getClass())
			return false;

		BaseEntity other = (BaseEntity) object;
		if (this.getId() != other.getId()
				&& (this.getId() == null || !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + " [ID=" + id + "]";
	}

}
