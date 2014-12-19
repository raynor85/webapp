package com.updapy.service.impl.retriever;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class WordPressRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "https://wordpress.org";
	private static final String DOWNLOAD_WEBSITE = ROOT_DOWNLOAD_WEBSITE + "/download/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("wordpress");
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
		return doc.select("a.download-button[href*=.zip]").attr("href");
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		try {
			return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, RemoteServiceImpl.retrieveHtmlDocumentAgent32(DOWNLOAD_WEBSITE).select("a.download-button[href*=.zip]").attr("href"));
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(doc.select("a.download-button").select("strong").text());
	}

}
