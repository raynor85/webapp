package com.updapy.service.retriever.impl.a;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class AdobeReaderRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://www.adobe.com/support/downloads/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("adobereader");
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
		return RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, doc.select("table").get(1).select("a:contains(Adobe Reader)[href*=ftpID]").first().attr("href"))).select("a:contains(Proceed to Download)").attr("href"))).select("a:contains(Download Now)").attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(doc.select("table").get(1).select("a:contains(Adobe Reader)[href*=ftpID]").first().text());
	}

}
