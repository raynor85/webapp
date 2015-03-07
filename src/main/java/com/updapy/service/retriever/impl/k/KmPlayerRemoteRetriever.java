package com.updapy.service.retriever.impl.k;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.HttpUtils;
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
		try {
			return HttpUtils.getRedirectionUrl(dlButton.select("a").attr("href"));
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		try {
			return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(HttpUtils.getFilenameFromUrl(retrieveWin32UrlEn(doc)), "_.*\\.exe"));
		} catch (IOException e) {
			return null;
		}
	}

}
