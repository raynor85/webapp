package com.updapy.service.retriever.impl.n;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.HttpUtils;
import com.updapy.util.ParsingUtils;

@Component
public class NortonSecurityRemoteRetriever implements RemoteRetriever {

	private static final String FR_DOWNLOAD_WEBSITE = "http://fr.norton.com/norton-security-downloads-trial";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("nortonsecurity");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) throws IOException {
		return getDownloadLink(RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(FR_DOWNLOAD_WEBSITE), "Version d'évaluation");
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return getDownloadLink(doc, "Download Now");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(ParsingUtils.selectFromPattern(HttpUtils.getRedirectionUrl(getDownloadLink(doc, "Download Now")), "/NS-TW-.*exe"));
	}

	public String getDownloadLink(Document doc, String buttonText) throws IOException {
		return doc.select("a:contains(" + buttonText + ")[href*=exe]").first().attr("href");
	}

}
