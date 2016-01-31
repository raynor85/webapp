package com.updapy.service.retriever.impl.d;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.service.retriever.impl.BaseUrlRemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class DropboxRemoteRetriever implements RemoteRetriever, BaseUrlRemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "https://www.dropbox.com";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("dropbox");
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
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, doc.select("div#full-download-link").select("a").attr("href"));
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(doc.select("span#version_str").text());
	}

	@Override
	public String getBaseUrl() {
		return ROOT_DOWNLOAD_WEBSITE;
	}

}
