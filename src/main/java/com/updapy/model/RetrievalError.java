package com.updapy.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.BaseEntity;
import com.updapy.model.enumeration.TypeRetrievalError;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "retrievalerror_seq")
public class RetrievalError extends BaseEntity {

	private int count;

	@Enumerated(EnumType.STRING)
	private TypeRetrievalError typeLastError;

	@ManyToOne(optional = false)
	private ApplicationReference application;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public TypeRetrievalError getTypeLastError() {
		return typeLastError;
	}

	public void setTypeLastError(TypeRetrievalError typeLastError) {
		this.typeLastError = typeLastError;
	}

	public ApplicationReference getApplication() {
		return application;
	}

	public void setApplication(ApplicationReference application) {
		this.application = application;
	}

}
