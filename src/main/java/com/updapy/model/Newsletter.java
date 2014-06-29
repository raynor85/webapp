package com.updapy.model;

import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.updapy.model.common.BaseEntity;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idSequence", sequenceName = "newsletter_seq")
public class Newsletter extends BaseEntity {

	private String subjectFr;

	private String subjectEn;

	private String titleFr;

	private String titleEn;

	private String textFr;

	private String textEn;

	private boolean active;

	private boolean notified;

	public String getSubjectFr() {
		return subjectFr;
	}

	public void setSubjectFr(String subjectFr) {
		this.subjectFr = subjectFr;
	}

	public String getSubjectEn() {
		return subjectEn;
	}

	public void setSubjectEn(String subjectEn) {
		this.subjectEn = subjectEn;
	}

	public String getTitleFr() {
		return titleFr;
	}

	public void setTitleFr(String titleFr) {
		this.titleFr = titleFr;
	}

	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	public String getTextFr() {
		return textFr;
	}

	public void setTextFr(String textFr) {
		this.textFr = textFr;
	}

	public String getTextEn() {
		return textEn;
	}

	public void setTextEn(String textEn) {
		this.textEn = textEn;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isNotified() {
		return notified;
	}

	public void setNotified(boolean notified) {
		this.notified = notified;
	}

	public String getSubject(Locale locale) {
		if (Locale.FRENCH.equals(locale)) {
			return subjectFr;
		}
		return subjectEn;
	}

	public String getTitle(Locale locale) {
		if (Locale.FRENCH.equals(locale)) {
			return titleFr;
		}
		return titleEn;
	}

	public String getText(Locale locale) {
		if (Locale.FRENCH.equals(locale)) {
			return textFr;
		}
		return textEn;
	}

}
