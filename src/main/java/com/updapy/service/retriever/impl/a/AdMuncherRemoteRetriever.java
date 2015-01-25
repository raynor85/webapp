package com.updapy.service.retriever.impl.a;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class AdMuncherRemoteRetriever implements RemoteRetriever {

	private static final String VERSION_HISTORY_WEBSITE = "https://www.admuncher.com/changelog.txt";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("admuncher");
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
		return doc.select("a:contains(click here)[href*=.exe]").attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		try {
			return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(RemoteServiceImpl.retrieveHtmlDocumentAgent32(VERSION_HISTORY_WEBSITE).select("body").text(), "Release date.*$"));
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

}
