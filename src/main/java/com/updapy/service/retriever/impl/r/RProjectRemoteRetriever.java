package com.updapy.service.retriever.impl.r;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.service.retriever.impl.BaseUrlRemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component(value = "rProjectRemoteRetriever")
public class RProjectRemoteRetriever implements RemoteRetriever, BaseUrlRemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "https://cran.r-project.org/bin/windows/base/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("rproject");
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
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, doc.select("a:contains(for Windows)[href*=.exe]").attr("href"));
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(doc.select("h1").text(), "\\(.*\\)"));
	}

	@Override
	public String getBaseUrl() {
		return ROOT_DOWNLOAD_WEBSITE;
	}
}
