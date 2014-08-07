package com.updapy.service.impl.retriever;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class ChromeRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "https://www.google.com/chrome/browser/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("chrome");
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
		return ROOT_DOWNLOAD_WEBSITE;
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(doc.select(".product-landing-quick-specs-row").select(".product-landing-quick-specs-row-content").get(0).text());
	}

}
