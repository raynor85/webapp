package com.updapy.service.retriever.impl.b;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class BaiduAntivirusRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("baiduantivirus");
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
		String dlLink = doc.select("a[href*=exe]:contains(Offline Installer)").attr("href");
		if (StringUtils.isBlank(dlLink)) {
			return doc.select("a#dlBtn[href*=exe]:contains(Download)").attr("href");
		}
		return dlLink;
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(doc.select("div.versioninfo").text(), "(s|S)ize.*"));
	}

}
