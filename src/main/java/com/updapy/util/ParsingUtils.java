package com.updapy.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ParsingUtils {

	public static final String HTTP_PREFIX = "http";
	public static final String HTTPS_PREFIX = "https";
	private static final String HTTP_SEPARATOR = "//";
	private static final String HTTP_PATH_SEPARATOR = "/";
	private static final String HTTP_PATH_RELATIVE = "." + HTTP_PATH_SEPARATOR;

	public static String selectFromPattern(String data, String pattern) {
		Pattern pat = Pattern.compile(pattern);
		Matcher matcher = pat.matcher(data);
		while (matcher.find()) {
			return matcher.group(0);
		}
		return null;
	}

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
		return addPrefix(url, HTTP_PREFIX);
	}

	public static String addHttpsPrefix(String url) {
		return addPrefix(url, HTTPS_PREFIX);
	}

	private static String addPrefix(String url, String prefix) {
		assert (prefix.equals(HTTPS_PREFIX) || prefix.equals(HTTP_PREFIX));
		if (url.startsWith(HTTP_SEPARATOR)) {
			return prefix + ':' + url;
		}
		if (url.startsWith(prefix)) {
			return url;
		}
		return prefix + ':' + HTTP_SEPARATOR + url;
	}

	public static String buildUrl(String rootUrl, String pageUrl) {
		if (pageUrl.startsWith(HTTP_PREFIX)) {
			return pageUrl;
		}
		if (pageUrl.startsWith(HTTP_PATH_RELATIVE)) {
			return buildUrl(rootUrl, StringUtils.removeStart(pageUrl, HTTP_PATH_RELATIVE));
		}
		if (pageUrl.startsWith(HTTP_SEPARATOR)) {
			return buildUrl(rootUrl, StringUtils.removeStart(pageUrl, HTTP_SEPARATOR));
		}
		return StringUtils.removeEnd(rootUrl, HTTP_PATH_SEPARATOR) + HTTP_PATH_SEPARATOR + StringUtils.removeStart(pageUrl.replaceAll("\\.\\." + HTTP_PATH_SEPARATOR, StringUtils.EMPTY), HTTP_PATH_SEPARATOR);
	}

	public String convertCharToDigit(String version) {
		Pattern pattern = Pattern.compile("[A-Za-z]");
		Matcher matcher = pattern.matcher(version);
		if (matcher.find()) {
			String versionWithoutLetters = matcher.replaceAll(Integer.toString(Character.getNumericValue(version.charAt(matcher.end() - 1)) - 9));
			return StringUtils.replaceChars(versionWithoutLetters, '-', '.');
		}
		return version;
	}

}
