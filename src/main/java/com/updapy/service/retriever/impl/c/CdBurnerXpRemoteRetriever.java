package com.updapy.service.retriever.impl.c;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class CdBurnerXpRemoteRetriever implements RemoteRetriever {

	private static final String DOWNLOAD_WEBSITE_VERSION = "https://cdburnerxp.se/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("cdburnerxp");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) throws IOException {
		return ParsingUtils.addHttpPrefix(doc.select("a[href*=exe]:contains(64 bit)").get(0).attr("href"));
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return ParsingUtils.addHttpPrefix(doc.select("a[href*=exe]:contains(32 bit)").get(0).attr("href"));
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(DOWNLOAD_WEBSITE_VERSION).select("span:containsOwn(Version)").html().split("<br>")[0]);
	}

}
