package com.updapy.service.retriever.impl.k;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.model.Version;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

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
		String version1 = ParsingUtils.extractVersionNumberFromString(doc.select("a:contains(Download KMPlayer)").text());
		String version2 = ParsingUtils.extractVersionNumberFromString(doc.select("dt:contains(Updated version)").get(0).text());
		if (version2 == null) {
			return version1;
		}
		if (new Version(version1).compareTo(new Version(version2)) == -1) {
			return version2;
		}
		return version1;
	}

}