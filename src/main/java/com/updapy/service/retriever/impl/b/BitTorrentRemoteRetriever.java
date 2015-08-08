package com.updapy.service.retriever.impl.b;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class BitTorrentRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://www.bittorrent.com/";
	private static final String DOWNLOAD_WEBSITE = ROOT_DOWNLOAD_WEBSITE + "downloads/win";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("bittorrent");
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
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(DOWNLOAD_WEBSITE).select("div#client-windows-stable").select("a:contains(Download Now)").attr("href"));
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(StringUtils.replacePattern(StringUtils.removePattern(RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(doc.select("a:contains(Current Stable Release)").attr("href")).select("div:contains(Version)").text(), "^.*Version"), "(B|b)uild", "."));
	}

}
