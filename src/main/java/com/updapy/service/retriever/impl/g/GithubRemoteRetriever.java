package com.updapy.service.retriever.impl.g;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class GithubRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "https://github.com";

	@Override
	public boolean support(ApplicationReference application) {
		String apiName = application.getApiName();
		return apiName.equalsIgnoreCase("docker") || apiName.equalsIgnoreCase("openbroadcastersoftware") || apiName.equalsIgnoreCase("clementineplayer");
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
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, doc.select("div.label-latest").select("a[href*=.exe]").attr("href"));
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(doc.select("div.label-latest").select("div.release-body").select("h1.release-title").text());
	}

}
