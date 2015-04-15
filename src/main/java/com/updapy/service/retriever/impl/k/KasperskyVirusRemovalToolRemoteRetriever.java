package com.updapy.service.retriever.impl.k;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class KasperskyVirusRemovalToolRemoteRetriever implements RemoteRetriever {

	private static final String DOWNLOAD_WEBSITE = "http://devbuilds.kaspersky-labs.com/devbuilds/AVPTool/index.js";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("kasperskyvirusremovaltool");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		try {
			return StringUtils.removePattern(StringUtils.removePattern(RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(DOWNLOAD_WEBSITE).select("body").text(), "^.*\"KAT-EN\" : \""), "\".*$");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		Pattern pattern = Pattern.compile("\\(.*\\)");
		String version = doc.select("td:contains(Version)").text();
		Matcher matcher = pattern.matcher(version);
		if (matcher.find()) {
			version = version.substring(matcher.start(), matcher.end());
		}
		return ParsingUtils.extractVersionNumberFromString(version);
	}

}
