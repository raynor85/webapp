package com.updapy.util;

import org.apache.commons.lang3.StringUtils;

public class ParsingUtils {

	public static String extractVersionNumberFromString(String str) {
		String cleanStr = StringUtils.removePattern(StringUtils.removePattern(StringUtils.removePattern(StringUtils.removePattern(str, "(U|u)pdate for.*$"), "for (W|w)indows.*$"), "(32|64).?(B|b)it"), "x(86|64)");
		return StringUtils.removePattern(StringUtils.removePattern(StringUtils.removePattern(cleanStr, "[^\\d.]"), "^\\.*"), "\\.*$");
	}

	public static String addHttpPrefix(String url) {
		if (url.startsWith("//")) {
			return "http" + url;
		}
		return url;
	}

}
