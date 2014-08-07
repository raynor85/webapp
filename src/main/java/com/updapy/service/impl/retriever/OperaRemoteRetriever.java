package com.updapy.service.impl.retriever;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class OperaRemoteRetriever implements RemoteRetriever {

	private static final String PATTERN_VERSION = "{version}";
	private static final String ROOT_DOWNLOAD_WEBSITE = "http://get.geo.opera.com/pub/opera/desktop/" + PATTERN_VERSION + "/win/Opera_" + PATTERN_VERSION + "_Setup.exe";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("opera");
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
		return StringUtils.replace(ROOT_DOWNLOAD_WEBSITE, PATTERN_VERSION, getVersionNumber(doc));
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return getVersionNumber(doc);
	}

	private String getVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(doc.select("ul.windows").select("a").get(0).text());
	}

}
