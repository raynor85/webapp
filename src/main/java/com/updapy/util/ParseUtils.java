package com.updapy.util;

import org.apache.commons.lang3.StringUtils;

public class ParseUtils {

	public static String extractVersionNumberFromString(String str) {
		String cleanStr = StringUtils.removePattern(StringUtils.removePattern(str, "(U|u)pdate for.*$"), "for (W|w)indows.*$");
		return StringUtils.removeEnd(StringUtils.removeStart(StringUtils.removePattern(cleanStr, "[^\\d.]"), "."), ".");
	}

}
