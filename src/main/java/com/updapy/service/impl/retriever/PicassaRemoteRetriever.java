package com.updapy.service.impl.retriever;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParseUtils;

@Component
public class PicassaRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference applicationReference) {
		return applicationReference.getName().equalsIgnoreCase("Picassa");
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
		return doc.select("p.windows").select("a:contains(Download Picasa)").attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParseUtils.extractVersionNumberFromString(doc.select("h2:contains(Picasa)").text());
	}

}
