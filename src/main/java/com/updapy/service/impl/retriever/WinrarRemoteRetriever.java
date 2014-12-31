package com.updapy.service.impl.retriever;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class WinrarRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://www.rarlab.com";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("winrar");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) {
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, doc.select("a:contains(French (64 bit))[href*=.exe]").get(0).attr("href"));
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) {
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, doc.select("a:contains(WinRAR x64 (64 bit))[href*=.exe]").get(0).attr("href"));
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) {
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, doc.select("a:contains(French (32 bit))[href*=.exe]").get(0).attr("href"));
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, doc.select("a:contains(WinRAR x86 (32 bit))[href*=.exe]").get(0).attr("href"));
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		String version = ParsingUtils.extractVersionNumberFromString(doc.select("a[href*=.exe]").get(0).text());
		if (version.contains("b")) {
			return "0"; // beta does not count
		}
		return version;
	}

}
