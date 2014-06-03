package com.updapy.service.impl.retriever;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParseUtils;

@Component
public class SevenZipRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference applicationReference) {
		return applicationReference.getName().equalsIgnoreCase("7-Zip");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) {
		return doc.select("a:contains(Download)[href*=x64]").attr("href");
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		return doc.select("a:contains(Download)").attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParseUtils.extractVersionNumberFromString(StringUtils.removePattern(StringUtils.removePattern(doc.select("th.title:contains(7-Zip)").html(), "7-Zip"), "<br.*$"));
	}

}
