package com.updapy.service.retriever.impl.i;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class InkscapeRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("inkscape");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) throws IOException {
		return getDownloadLink(doc, "64bit");
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return getDownloadLink(doc, "32bit");
	}

	private String getDownloadLink(Document doc, String version) {
		Elements elements = doc.select("p:contains(" + version + ")");
		if (elements.select("a:contains(exe)").first() == null) {
			return elements.select("a:contains(msi)").first().attr("href");
		}
		return elements.select("a:contains(exe)").first().attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(doc.select("h2:contains(Latest)").text());
	}

}
