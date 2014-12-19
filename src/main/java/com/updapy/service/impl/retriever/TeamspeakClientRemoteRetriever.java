package com.updapy.service.impl.retriever;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class TeamspeakClientRemoteRetriever implements RemoteRetriever {

	private static final String PATTERN_VERSION = "{version}";
	private static final String DOWNLOAD_WEBSITE_32 = "http://dl.4players.de/ts/releases/" + PATTERN_VERSION + "/TeamSpeak3-Client-win32-" + PATTERN_VERSION + ".exe";
	private static final String DOWNLOAD_WEBSITE_64 = "http://dl.4players.de/ts/releases/" + PATTERN_VERSION + "/TeamSpeak3-Client-win64-" + PATTERN_VERSION + ".exe";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("teamspeakclient");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) {
		return StringUtils.replace(DOWNLOAD_WEBSITE_64, PATTERN_VERSION, getVersionNumber(doc));
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		return StringUtils.replace(DOWNLOAD_WEBSITE_32, PATTERN_VERSION, getVersionNumber(doc));
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return getVersionNumber(doc);
	}

	private String getVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(doc.select("table.download_category_content").get(0).select("td:contains(Client)").get(0).text(), "SHA1.*$"));
	}
}
