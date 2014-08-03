package com.updapy.service.impl.retriever;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class TorBrowserRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "https://www.torproject.org";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getName().equalsIgnoreCase("Tor Browser");
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
		return ROOT_DOWNLOAD_WEBSITE + StringUtils.removeStart(doc.select("a[href*=fr.exe]").get(0).attr("href"), "..");
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		return ROOT_DOWNLOAD_WEBSITE + StringUtils.removeStart(doc.select("a[href*=en-US.exe]").get(0).attr("href"), "..");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(doc.select("div.dltable").select("table").select("thead").select("th").get(1).text());
	}

}
