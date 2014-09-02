package com.updapy.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ParsingUtils {

	public static String extractVersionNumberFromString(String str) {
		List<String> patternToRemoves = new ArrayList<String>();
		patternToRemoves.add("(U|u)pdate for.*$");
		patternToRemoves.add("(F|f)or (W|w)indows.*$");
		patternToRemoves.add("(32|64).?(B|b)it");
		patternToRemoves.add("x(86|64)");
		patternToRemoves.add("win.?(32|64)");
		patternToRemoves.add("[^\\d.]");
		patternToRemoves.add("^\\.*");
		patternToRemoves.add("\\.*$");

		String cleanStr = str;
		for (String patternToRemove : patternToRemoves) {
			cleanStr = StringUtils.removePattern(cleanStr, patternToRemove);
		}
		return cleanStr;
	}

	public static String addHttpPrefix(String url) {
		if (url.startsWith("//")) {
			return "http:" + url;
		}
		if (url.startsWith("http")) {
			return url;
		}
		return "http://" + url;
	}

}
