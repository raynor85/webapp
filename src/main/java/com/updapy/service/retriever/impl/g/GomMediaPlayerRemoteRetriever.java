package com.updapy.service.retriever.impl.g;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class GomMediaPlayerRemoteRetriever implements RemoteRetriever {

	private static final String VERSION_HISTORY_WEBSITE = "http://player.gomlab.com/history.gom?language=eng";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("gommediaplayer");
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
		return "https://app.gomlab.com/site/player/GOMPLAYERENSETUP.EXE";
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(VERSION_HISTORY_WEBSITE).select("table.tbl_history").select("tbody").select("th").first().html().split("<br")[0]);
	}

}
