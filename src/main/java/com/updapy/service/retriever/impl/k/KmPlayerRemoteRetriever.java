package com.updapy.service.retriever.impl.k;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;

@Component
public class KmPlayerRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("kmplayer");
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
		Elements buttons = doc.select("div[class^=dm_btn]");
		Element dlButton;
		if (buttons.size() > 1) {
			dlButton = buttons.get(1);
		} else {
			dlButton = buttons.get(0);
		}
		return dlButton.select("a").attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		String link = retrieveWin32UrlEn(doc);
		// TODO retrieve the filename to retrieve the version
		return null;
	}

}
