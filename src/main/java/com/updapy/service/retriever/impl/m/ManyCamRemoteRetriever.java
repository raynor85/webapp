package com.updapy.service.retriever.impl.m;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class ManyCamRemoteRetriever implements RemoteRetriever {

	private static final String VERSION_HISTORY_WEBSITE = "https://download.manycam.com/win_changes";
	private static final String DOWNLOAD_WEBSITE = "https://download.manycam.com/thank_you_page?os=win";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("manycam");
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
		return ParsingUtils.selectFromPattern(RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(DOWNLOAD_WEBSITE).select("a:containsOwn(click here)[onclick*=exe]").first().attr("onclick"), "http.*.exe");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(VERSION_HISTORY_WEBSITE).select("p:containsOwn(Version)").first().text(), "\\(.*\\)"));
	}

}
