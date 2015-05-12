package com.updapy.service.retriever.impl.k;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class KasperskySecurityScanRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://devbuilds.kaspersky-labs.com/devbuilds/KSS/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("kasperskysecurityscan");
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
		try {
			String url = ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, getDownloadLink(doc).attr("href"));
			return ParsingUtils.buildUrl(url, RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(url).select("a[href*=.msi]").attr("href"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(getDownloadLink(doc).text());
	}

	private Element getDownloadLink(Document doc) {
		return doc.select("a").last();
	}

}
