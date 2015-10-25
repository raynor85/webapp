package com.updapy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.updapy.model.common.BaseEntity;
import com.updapy.model.stats.Rating;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "application_description_seq")
public class ApplicationDescription extends BaseEntity {

	@OneToOne(fetch = FetchType.EAGER, optional = false)
	private ApplicationReference application;

	@Column(columnDefinition = "text")
	private String descriptionEn;

	@Column(columnDefinition = "text")
	private String descriptionFr;

	@Transient
	private Rating rating;

	public ApplicationReference getApplication() {
		return application;
	}

	public void setApplication(ApplicationReference application) {
		this.application = application;
	}

	public String getDescriptionEn() {
		return descriptionEn;
	}

	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}

	public String getDescriptionFr() {
		return descriptionFr;
	}

	public void setDescriptionFr(String descriptionFr) {
		this.descriptionFr = descriptionFr;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

}
