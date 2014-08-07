package com.updapy.service.impl.retriever;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class EclipseJeeRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http:";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("eclipsejee");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) {
		return ROOT_DOWNLOAD_WEBSITE + doc.select("a.downloadLink:contains(Windows 64 Bit)[href*=jee]").attr("href");
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		return ROOT_DOWNLOAD_WEBSITE + doc.select("a.downloadLink:contains(Windows 32 Bit)[href*=jee]").attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(doc.select("#descriptionText").text());
	}

}
