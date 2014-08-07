package com.updapy.service.impl.retriever;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class TeamViewerRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("teamviewer");
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
		return StringUtils.replacePattern(doc.select("a.button-a[href*=.exe]").get(0).attr("href"), "_Setup.*.exe", "_Setup_fr.exe");
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		return StringUtils.replacePattern(doc.select("a.button-a[href*=.exe]").get(0).attr("href"), "_Setup.*.exe", "_Setup_en.exe");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(doc.select("span.download-button-windows").get(0).text());
	}

}
