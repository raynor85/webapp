package com.updapy.service.retriever.impl.c;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.HttpUtils;
import com.updapy.util.ParsingUtils;

@Component
public class CloudSystemBoosterRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://www.anvisoft.com/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("cloudsystembooster");
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
			return HttpUtils.getRedirectionUrl(ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, StringUtils.removeStart(StringUtils.removeEnd(ParsingUtils.selectFromPattern(doc.select("a:contains(Free Trial)").attr("onclick"), ",'.*'"), "'"), ",'")));
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(doc.select("h2.tit:contains(Cloud System Booster)").first().text());
	}

}