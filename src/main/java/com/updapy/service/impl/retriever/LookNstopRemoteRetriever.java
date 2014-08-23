package com.updapy.service.impl.retriever;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class LookNstopRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://www.looknstop.com/";
	private static final String SUFFIX_EN = "En/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("looknstop");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) {
		return ROOT_DOWNLOAD_WEBSITE + StringUtils.removeStart(doc.select("a[href*=Installation]").select("a[href*=x64]").get(0).attr("href"), "..");
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) {
		return ROOT_DOWNLOAD_WEBSITE + SUFFIX_EN + doc.select("a[href*=Setup]").select("a[href*=x64]").get(0).attr("href");
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) {
		return ROOT_DOWNLOAD_WEBSITE + StringUtils.removeStart(doc.select("a[href*=Installation]").get(0).attr("href"), "..");
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		return ROOT_DOWNLOAD_WEBSITE + SUFFIX_EN + doc.select("a[href*=Setup]").get(0).attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(doc.select("font:contains(Download Look 'n' Stop Firewall)").text());
	}

}
