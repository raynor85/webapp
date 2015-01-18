package com.updapy.service.retriever.impl.y;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class YahooMessengerRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE_VERSION_FR = "https://fr.messenger.yahoo.com/download/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("yahoomessenger");
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
		try {
			return getDownloadLink(RemoteServiceImpl.retrieveHtmlDocumentAgent32(ROOT_DOWNLOAD_WEBSITE_VERSION_FR));
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		return getDownloadLink(doc);
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(doc.select("span.sgray").first().text());
	}

	private String getDownloadLink(Document doc) {
		return doc.select("a#messenger-download").attr("href");
	}

}
