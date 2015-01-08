package com.updapy.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ParsingUtils {

	private static final String HTTP_PREFIX = "http";
	private static final String HTTP_SEPARATOR = "//";
	private static final String HTTP_PATH_SEPARATOR = "/";

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
		if (url.startsWith(HTTP_SEPARATOR)) {
			return HTTP_PREFIX + ':' + url;
		}
		if (url.startsWith(HTTP_PREFIX)) {
			return url;
		}
		return HTTP_PREFIX + ':' + HTTP_SEPARATOR + url;
	}

	public static String buildUrl(String rootUrl, String pageUrl) {
		if (pageUrl.startsWith(HTTP_PREFIX)) {
			return pageUrl;
		}
		return StringUtils.removeEnd(rootUrl, HTTP_PATH_SEPARATOR) + HTTP_PATH_SEPARATOR + pageUrl.replaceAll("\\.\\." + HTTP_PATH_SEPARATOR, StringUtils.EMPTY);
	}

}
