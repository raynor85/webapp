package com.updapy.service.retriever.impl.v;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class VlcMediaPlayerRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("vlcmediaplayer");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) throws IOException {
		String dlLink = ParsingUtils.addHttpPrefix(doc.select("a[href*=win64]").attr("href"));
		return ParsingUtils.buildUrl(dlLink, RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(dlLink).select("a:contains(.exe)[href*=.exe]").first().attr("href"));
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		String dlLink = ParsingUtils.addHttpPrefix(doc.select("a[href*=win32]").attr("href"));
		return ParsingUtils.buildUrl(dlLink, RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(dlLink).select("a:contains(.exe)[href*=.exe]").first().attr("href"));
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(doc.select("span#downloadVersion").text());
	}

}
