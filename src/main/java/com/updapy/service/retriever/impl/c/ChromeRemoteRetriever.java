package com.updapy.service.retriever.impl.c;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class ChromeRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "https://dl.google.com/chrome/install/";
	private static final String PLATFORM_32_BIT = "standalonesetup.exe";
	private static final String PLATFORM_64_BIT = "standalonesetup64.exe ";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("chrome");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) throws IOException {
		return ROOT_DOWNLOAD_WEBSITE + PLATFORM_64_BIT;
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return ROOT_DOWNLOAD_WEBSITE + PLATFORM_32_BIT;
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		Elements versionElement = doc.select(".product-landing-quick-specs-row");
		if (!versionElement.isEmpty()) {
			return ParsingUtils.extractVersionNumberFromString(versionElement.select(".product-landing-quick-specs-row-content").get(0).text());
		} else {
			return ParsingUtils.extractVersionNumberFromString(doc.select("tr#specsPubVersion").text());
		}
	}

}
