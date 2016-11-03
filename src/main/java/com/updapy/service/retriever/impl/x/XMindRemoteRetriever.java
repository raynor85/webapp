package com.updapy.service.retriever.impl.x;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component(value = "xMindRemoteRetriever")
public class XMindRemoteRetriever implements RemoteRetriever {

	private static final String VERSION_HISTORY_WEBSITE = "http://www.xmind.net/download/previous/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("xmind");
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
		return doc.select("a:contains(Download XMind)[href*=.exe]").attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(ParsingUtils.selectFromPattern(RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(VERSION_HISTORY_WEBSITE).select("p:contains(latest)").text(), "\\(.*\\)"), ".*v"));
	}

}
