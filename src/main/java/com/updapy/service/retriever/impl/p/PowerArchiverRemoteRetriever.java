package com.updapy.service.retriever.impl.p;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class PowerArchiverRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("powerarchiver");
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
		return doc.select("a:contains(download)[href*=exe]").first().attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(doc.select("span:containsOwn(Version)").get(0).text(), "^.*, (V|v)ersion"));
	}

}
