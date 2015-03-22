package com.updapy.service.retriever.impl.u;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component(value = "uTorrentRemoteRetriever")
public class UTorrentRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://www.utorrent.com/";

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
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, doc.select("a:contains(Download Now)").attr("href"));
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(StringUtils.replacePattern(doc.select("h3:contains(Stable)").text(), "(B|b)uild", "."));
	}

}
