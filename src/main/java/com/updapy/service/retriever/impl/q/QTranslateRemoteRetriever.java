package com.updapy.service.retriever.impl.q;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component(value = "qTranslateRemoteRetriever")
public class QTranslateRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http:";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("qtranslate");
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
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, doc.select("a.btn-download[href*=exe]").first().attr("href"));
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(doc.select("h3").first().text());
	}

}
