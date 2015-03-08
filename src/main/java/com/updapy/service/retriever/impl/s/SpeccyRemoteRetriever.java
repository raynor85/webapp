package com.updapy.service.retriever.impl.s;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class SpeccyRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://www.piriform.com/speccy/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("speccy");
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
			return RemoteServiceImpl.retrieveHtmlDocumentAgent32(ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, doc.select("a[href*=download/standard]").get(0).attr("href"))).select("a:contains(click here)[href*=.exe]").attr("href");
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(doc.select("ul.versionHistory").select("li").select("strong").get(0).text());
	}

}
