package com.updapy.service.retriever.impl.b;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class BitTorrentSyncRemoteRetriever implements RemoteRetriever {

	private static final String VERSION_HISTORY_WEBSITE = "http://sync-help.bittorrent.com/customer/portal/articles/1767561-sync-change-log";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("bittorrentsync");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) {
		return doc.select("button.gat-Downloads-Download_Links-Windows64").get(0).attr("href");
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		return doc.select("button.gat-Downloads-Download_Links-Windows32").get(0).attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		try {
			return ParsingUtils.extractVersionNumberFromString(RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(VERSION_HISTORY_WEBSITE).select("#support-main").select("strong").first().text());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
