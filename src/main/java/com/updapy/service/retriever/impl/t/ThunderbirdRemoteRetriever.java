package com.updapy.service.retriever.impl.t;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class ThunderbirdRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("thunderbird");
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
		return doc.select("#fr").select("td.win").select("a").attr("href");
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return getDownloadLink(doc);
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		Element version = doc.select("td.curVersion").first();
		if (version == null) {
			return ParsingUtils.extractVersionNumberFromString(getDownloadLink(doc));
		}
		return ParsingUtils.extractVersionNumberFromString(version.text());
	}

	private String getDownloadLink(Document doc) {
		return doc.select("#en-US").select("td.win").select("a").attr("href");
	}
}
