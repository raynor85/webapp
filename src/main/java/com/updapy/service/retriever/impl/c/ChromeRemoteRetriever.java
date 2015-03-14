package com.updapy.service.retriever.impl.c;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class ChromeRemoteRetriever implements RemoteRetriever {

	private static final String DOWNLOAD_WEBSITE_PLATFORM_32_BIT = "https://dl.google.com/chrome/install/standalonesetup.exe";
	private static final String DOWNLOAD_WEBSITE_PLATFORM_64_BIT = "https://dl.google.com/chrome/install/standalonesetup64.exe";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("chrome");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) {
		return DOWNLOAD_WEBSITE_PLATFORM_64_BIT;
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		return DOWNLOAD_WEBSITE_PLATFORM_32_BIT;
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		Elements versionElement = doc.select(".product-landing-quick-specs-row");
		if (!versionElement.isEmpty()) {
			return ParsingUtils.extractVersionNumberFromString(versionElement.select(".product-landing-quick-specs-row-content").get(0).text());
		} else {
			return ParsingUtils.extractVersionNumberFromString(doc.select("tr#specsPubVersion").text());
		}
	}

}
