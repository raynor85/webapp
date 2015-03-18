package com.updapy.service.retriever.impl.t;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;

@Component
public class TotalCommanderRemoteRetriever implements RemoteRetriever {

	private static final String DOWNLOAD_WEBSITE = "http://www.ghisler.com/download.htm";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("totalcommander");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) {
		return doc.select("a[href*=exe][href*=64]").first().attr("href");
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		return doc.select("a[href*=exe][href*=32]").first().attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		try {
			String version = StringUtils.removePattern(StringUtils.removePattern(RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(DOWNLOAD_WEBSITE).select("h3:contains(Download)").get(0).html().split("<br>")[0], "^.*Download version( |)"), "( |)of.*$");
			return convertCharToDigit(version);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	private String convertCharToDigit(String version) {
		Pattern pattern = Pattern.compile("[A-Za-z]");
		Matcher matcher = pattern.matcher(version);
		if (matcher.find()) {
			return matcher.replaceAll('.' + Integer.toString(Character.getNumericValue(version.charAt(matcher.end() - 1)) - 9));
		}
		return version;
	}

}
