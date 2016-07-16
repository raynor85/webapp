package com.updapy.service.retriever.impl.w;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.service.retriever.impl.BaseUrlRemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class WinrarRemoteRetriever implements RemoteRetriever, BaseUrlRemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://www.rarlab.com";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("winrar");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) throws IOException {
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, doc.select("a:contains(French (64 bit))[href*=.exe]").get(0).attr("href"));
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) throws IOException {
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, doc.select("a:contains(WinRAR x64 (64 bit))[href*=.exe]").get(0).attr("href"));
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) throws IOException {
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, doc.select("a:contains(French (32 bit))[href*=.exe]").get(0).attr("href"));
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, doc.select("a:contains(WinRAR x86 (32 bit))[href*=.exe]").get(0).attr("href"));
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		String versionFull = doc.select("a[href*=.exe]").get(0).text();
		if (StringUtils.containsIgnoreCase(versionFull, "beta")) {
			return RemoteServiceImpl.VERSION_NOT_FOUND; // beta does not count
		}
		return ParsingUtils.extractVersionNumberFromString(versionFull);
	}

	@Override
	public String getBaseUrl() {
		return ROOT_DOWNLOAD_WEBSITE;
	}

}
