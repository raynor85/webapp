package com.updapy.service.impl.retriever;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;

@Component
public class PaintNetRemoteRetriever implements RemoteRetriever {

	static final String ROOT_DOWNLOAD_WEBSITE = "http://www.dotpdn.com";

	@Override
	public boolean support(ApplicationReference applicationReference) {
		return applicationReference.getName().equalsIgnoreCase("Paint.NET");
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
		return ROOT_DOWNLOAD_WEBSITE + StringUtils.removeStart(doc.select("a:contains(Paint.NET)").first().attr("href"), "..");
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		return ROOT_DOWNLOAD_WEBSITE + StringUtils.removeStart(doc.select("a:contains(Paint.NET)").first().attr("href"), "..");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return StringUtils.removeStart(doc.select("a:contains(Paint.NET)").first().text(), "Paint.NET v");
	}

}
