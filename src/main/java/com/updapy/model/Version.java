package com.updapy.model;

import org.apache.commons.lang3.StringUtils;

public class Version implements Comparable<Version> {

	private String version;

	public final String get() {
		return this.version;
	}

	public Version(String version) {
		if (version == null)
			throw new IllegalArgumentException("Version can not be null");
		if (!version.matches("[0-9]+(\\.[0-9]+)*"))
			throw new IllegalArgumentException("Invalid version format");
		this.version = version;
	}

	@Override
	public int compareTo(Version that) {
		if (that == null)
			return 1;
		String[] thisParts = this.get().split("\\.");
		String[] thatParts = that.get().split("\\.");
		int length = Math.max(thisParts.length, thatParts.length);
		for (int i = 0; i < length; i++) {
			String thisPartStr = thisParts[i];
			String thatPartStr = thatParts[i];
			if ((StringUtils.startsWith(thisPartStr, "0") && !StringUtils.startsWith(thatPartStr, "0")) || (!StringUtils.startsWith(thisPartStr, "0") && StringUtils.startsWith(thatPartStr, "0"))) {
				thisPartStr = StringUtils.left(thisParts[i], 1);
				thatPartStr = StringUtils.left(thatParts[i], 1);
			}
			int thisPart = i < thisParts.length ? Integer.parseInt(thisPartStr) : 0;
			int thatPart = i < thatParts.length ? Integer.parseInt(thatPartStr) : 0;
			if (thisPart < thatPart)
				return -1;
			if (thisPart > thatPart)
				return 1;
		}
		return 0;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that)
			return true;
		if (that == null)
			return false;
		if (this.getClass() != that.getClass())
			return false;
		return this.compareTo((Version) that) == 0;
	}

}
