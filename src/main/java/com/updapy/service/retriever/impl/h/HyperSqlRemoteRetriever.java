package com.updapy.service.retriever.impl.h;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.service.retriever.impl.BaseUrlRemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class HyperSqlRemoteRetriever implements RemoteRetriever, BaseUrlRemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "https://sourceforge.net/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("hypersql");
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
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		String link = getDownloadLink(doc).attr("href");
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(link).select("div:containsOwn(Looking for the latest version)").select("a").first().attr("href"));
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(getDownloadLink(doc).text());
	}

	private Element getDownloadLink(Document doc) {
		return doc.select("a:contains(Download latest version)").first();
	}

	@Override
	public String getBaseUrl() {
		return ROOT_DOWNLOAD_WEBSITE;
	}

}
