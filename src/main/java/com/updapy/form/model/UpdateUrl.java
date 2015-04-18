package com.updapy.form.model;

import org.apache.commons.lang3.StringUtils;

import com.updapy.model.enumeration.Lang;
import com.updapy.model.enumeration.OsVersion;

public class UpdateUrl {

	private String url;

	private Lang lang;

	private OsVersion osVersion;

	public UpdateUrl(String url, Lang lang, OsVersion osVersion) {
		this.url = url;
		this.lang = lang;
		this.osVersion = osVersion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Lang getLang() {
		return lang;
	}

	public void setLang(Lang lang) {
		this.lang = lang;
	}

	public OsVersion getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(OsVersion osVersion) {
		this.osVersion = osVersion;
	}

	public String getMessageKey() {
		return lang.name() + '.' + StringUtils.lowerCase(osVersion.name());
	}

}
