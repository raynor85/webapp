package com.updapy.service.retriever.impl.s;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class SecurityEssentialsRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://www.microsoft.com/";
	private static final String ROOT_DOWNLOAD_WEBSITE_VERSION_FR = ROOT_DOWNLOAD_WEBSITE + "fr-FR/download/";
	private static final String ROOT_DOWNLOAD_WEBSITE_VERSION_EN = ROOT_DOWNLOAD_WEBSITE + "en-gb/download/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("securityessentials");
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
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE_VERSION_FR, getDownloadLink(doc));
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE_VERSION_EN, getDownloadLink(doc));
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(doc.select("span:containsOwn(Version)").parents().select("p").first().text());
	}

	private String getDownloadLink(Document doc) {
		return doc.select("a.download-button").first().attr("href");
	}

}
