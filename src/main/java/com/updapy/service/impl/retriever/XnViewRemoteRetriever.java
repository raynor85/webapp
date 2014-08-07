package com.updapy.service.impl.retriever;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class XnViewRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("xnview");
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
		return doc.select("#downloads").select("a:contains(XnView Std. Setup)[href*=.exe]").attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(doc.select("#downloads").select("p.lead").text());
	}

}
