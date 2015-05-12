package com.updapy.service.retriever.impl.r;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class RealVncRemoteRetriever implements RemoteRetriever {

	private static final String PATTERN_ID = "{id}";
	private static final String DOWNLOAD_WEBSITE = "https://www.realvnc.com/download/binary/" + PATTERN_ID + "/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("realvnc");
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
		return DOWNLOAD_WEBSITE.replace(PATTERN_ID, ParsingUtils.extractVersionNumberFromString(doc.select("h2.title:contains(Windows)").parents().select("ul.binaries").select("a").get(0).attr("href")));
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(doc.select("h2.title:contains(Windows)").parents().select("li.version").get(0).text());
	}

}
