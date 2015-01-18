package com.updapy.service.retriever.impl.f;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class FlashPlayerRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("flashplayer");
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
		return doc.select("a:contains(Download EXE Installer):not([href*=active_x])").get(0).attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		String title = doc.select(".TextH3:contains(Win)").text();
		if (StringUtils.contains(title, ';')) {
			String[] titles = StringUtils.split(title, ';');
			String toFind = "firefox";
			if (StringUtils.containsIgnoreCase(titles[0], toFind)) {
				return ParsingUtils.extractVersionNumberFromString(titles[0]);
			} else if (StringUtils.containsIgnoreCase(titles[1], toFind)) {
				return ParsingUtils.extractVersionNumberFromString(titles[1]);
			}
		}
		return ParsingUtils.extractVersionNumberFromString(title);
	}

}
