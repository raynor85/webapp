package com.updapy.service.retriever.impl.n;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class NortonSecurityRemoteRetriever implements RemoteRetriever {

	private static final String PATTERN_VERSION_SHORT = "{version_short}";
	private static final String PATTERN_VERSION = "{version}";
	private static final String DOWNLOAD_WEBSITE = "http://buy-download.norton.com/downloads/2015/" + PATTERN_VERSION_SHORT + "/NISNAV/US/NIS-ESD-" + PATTERN_VERSION + "-EN.exe";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("nortonsecurity");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		String version = getVersionNumber(doc);
		String[] versionSplited = version.split("\\.");
		return StringUtils.replace(StringUtils.replace(DOWNLOAD_WEBSITE, PATTERN_VERSION, version), PATTERN_VERSION_SHORT, versionSplited[0] + '.' + versionSplited[1]);
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return getVersionNumber(doc);
	}

	private String getVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(doc.select("h1:contains(Norton Security)").text());

	}
}
