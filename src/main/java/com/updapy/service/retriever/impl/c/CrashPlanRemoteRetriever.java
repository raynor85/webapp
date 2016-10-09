package com.updapy.service.retriever.impl.c;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class CrashPlanRemoteRetriever implements RemoteRetriever {

	private static final String PATTERN_VERSION = "{version}";
	private static final String PATTERN_PLATFORM = "{platform}";
	private static final String DOWNLOAD_WEBSITE = "https://download.code42.com/installs/win/install/CrashPlan/jre/CrashPlan_" + PATTERN_VERSION + "_" + PATTERN_PLATFORM + ".msi";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("crashplan");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) throws IOException {
		return StringUtils.replace(StringUtils.replace(DOWNLOAD_WEBSITE, PATTERN_VERSION, getVersionNumber(doc)), PATTERN_PLATFORM, "Win64");
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return StringUtils.replace(StringUtils.replace(DOWNLOAD_WEBSITE, PATTERN_VERSION, getVersionNumber(doc)), PATTERN_PLATFORM, "Win");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return getVersionNumber(doc);
	}

	private String getVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(StringUtils.removePattern(doc.select("body").text(), "cp.download.CPPRO_CLIENT_VERSION.*$"), "^.*cp.download.CPC_CLIENT_VERSION"));
	}
}
