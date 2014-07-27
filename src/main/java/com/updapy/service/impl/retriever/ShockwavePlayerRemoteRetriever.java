package com.updapy.service.impl.retriever;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class ShockwavePlayerRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://www.adobe.com";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getName().equalsIgnoreCase("Shockwave Player");
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
		return ROOT_DOWNLOAD_WEBSITE + doc.select("a:contains(Download Full EXE Installer)").get(0).attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		String version = ParsingUtils.extractVersionNumberFromString(doc.select(".TextH3:contains(Downloads)").text());
		if (StringUtils.isBlank(version)) {
			return "12";
		}
		return version;
	}

}
