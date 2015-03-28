package com.updapy.service.retriever.impl.b;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.HttpUtils;
import com.updapy.util.ParsingUtils;

@Component
public class BandisoftRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://www.bandisoft.com";

	@Override
	public boolean support(ApplicationReference application) {
		String apiName = application.getApiName();
		return apiName.equalsIgnoreCase("bandizip") || apiName.equalsIgnoreCase("honeyview");
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
		try {
			return HttpUtils.getRedirectionUrl(ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, doc.select("a:contains((Bandisoft))").attr("href"))).select("li:contains(from Bandisoft)").select("a").attr("href")));
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(doc.select("a:contains(History)").parents().text(), "-.*"));
	}

}
