package com.updapy.service.impl.retriever;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class Foobar2000RemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://www.foobar2000.org";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("foobar2000");
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
		return ROOT_DOWNLOAD_WEBSITE + doc.select("a[href*=.exe]").get(0).attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(doc.select("a[href*=.exe]").get(0).text(), "(F|f)oobar2000"));
	}

}