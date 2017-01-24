package com.updapy.service.retriever.impl.f;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;

@Component
public class FirefoxRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("firefox");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) throws IOException {
		return doc.select("#fr").select(".win64").select("a").attr("href");
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) throws IOException {
		return doc.select("#en-US").select(".win64").select("a").attr("href");
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) throws IOException {
		return doc.select("#fr").select(".win").select("a").attr("href");
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return doc.select("#en-US").select(".win").select("a").attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return doc.select("html").attr("data-latest-firefox");
	}

}
