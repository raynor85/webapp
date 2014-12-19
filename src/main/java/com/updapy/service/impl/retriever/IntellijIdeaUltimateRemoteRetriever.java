package com.updapy.service.impl.retriever;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class IntellijIdeaUltimateRemoteRetriever implements RemoteRetriever {

	private static final String PATTERN_VERSION = "{version}";
	private static final String DOWNLOAD_WEBSITE = "http://download-cf.jetbrains.com/idea/ideaIU-" + PATTERN_VERSION + ".exe";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("intellijideaultimate");
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
		return StringUtils.replace(DOWNLOAD_WEBSITE, PATTERN_VERSION, StringUtils.removeEnd(getVersionNumber(doc), ".0"));
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return getVersionNumber(doc);
	}

	private String getVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(StringUtils.removePattern(doc.select("body").text(), "function versionIDEA\\(el\\).*$"), "^.*versionIDEALong"));
	}

}
