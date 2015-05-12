package com.updapy.service.retriever.impl.d;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class DriverGeniusRemoteRetriever implements RemoteRetriever {

	private final String ROOT_DOWNLOAD_WEBSITE = "http://www.driver-soft.com/";
	private final String DOWNLOAD_WEBSITE = ROOT_DOWNLOAD_WEBSITE;

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("drivergenius");
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
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(DOWNLOAD_WEBSITE).select("a[href*=exe]").attr("href"));
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(ParsingUtils.selectFromPattern(doc.select("span.STYLE2:contains(Version:)").first().text(), "\\(.*\\)"));
	}

}
