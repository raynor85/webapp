package com.updapy.service.impl.retriever;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;

@Component
public class FirefoxRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getName().equalsIgnoreCase("Firefox");
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
		return doc.select("#fr").select(".win").select("a").attr("href");
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		return doc.select("#en-US").select(".win").select("a").attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return doc.select("html").attr("data-latest-firefox");
	}

}
