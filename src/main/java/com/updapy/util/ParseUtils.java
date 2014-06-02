package com.updapy.util;

import org.apache.commons.lang3.StringUtils;

public class ParseUtils {

	public static String extractVersionNumberFromString(String str) {
		return StringUtils.removeEnd(StringUtils.removeStart(StringUtils.removePattern(str, "[^\\d.]"), "."), ".");
	}

}
