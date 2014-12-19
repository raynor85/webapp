package com.updapy.service.impl.retriever;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class AnyDeskRemoteRetriever implements RemoteRetriever {

	private static final String VERSION_HISTORY_WEBSITE = "http://www.snapfiles.com/apphistory/anydesk_history.html";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("anydesk");
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
		return doc.select("a[href*=.exe]").get(0).attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		try {
			return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(RemoteServiceImpl.retrieveHtmlDocumentAgent32(VERSION_HISTORY_WEBSITE).select("h3").get(0).html(), "<.*>.*</.*>"));
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

}
