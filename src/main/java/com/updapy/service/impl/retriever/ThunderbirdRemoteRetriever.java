package com.updapy.service.impl.retriever;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class ThunderbirdRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference applicationReference) {
		return applicationReference.getName().equalsIgnoreCase("Thunderbird");
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
		return doc.select("#fr").select("a.download-windows").attr("href");
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		return doc.select("#en-US").select("a.download-windows").attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(doc.select("td.curVersion").first().text());
	}

}
