package com.updapy.service.retriever.impl.n;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class nXPowerliteRemoteRetriever implements RemoteRetriever {

	private static final String VERSION_HISTORY_WEBSITE = "http://support.neuxpower.com/hc/en-us/articles/207614019-Release-history-NXPowerLite-Desktop-Windows";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("nxpowerlite");
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
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return doc.select("a:contains(click here)").attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(VERSION_HISTORY_WEBSITE).select("div.article-body").select("p").first().select("span").first().text());
	}

}
