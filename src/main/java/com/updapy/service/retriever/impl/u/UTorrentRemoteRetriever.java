package com.updapy.service.retriever.impl.u;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component(value = "uTorrentRemoteRetriever")
public class UTorrentRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://www.utorrent.com/";
	private static final String DOWNLOAD_WEBSITE = ROOT_DOWNLOAD_WEBSITE + "downloads/win";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("utorrent");
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
			return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(DOWNLOAD_WEBSITE).select("a:contains(Download Now)").attr("href"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		try {
			return ParsingUtils.extractVersionNumberFromString(StringUtils.replacePattern(StringUtils.removePattern(RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(doc.select("a:contains(Current Stable Release)").attr("href")).select("div:contains(Version)").text(), "^.*Version"), "(B|b)uild", "."));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
