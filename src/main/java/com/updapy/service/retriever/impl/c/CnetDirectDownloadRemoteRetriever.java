package com.updapy.service.retriever.impl.c;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class CnetDirectDownloadRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		String apiName = application.getApiName();
		return apiName.equalsIgnoreCase("spotify") || apiName.equalsIgnoreCase("daemontoolslite") || apiName.equalsIgnoreCase("wiseuninstaller") || apiName.equalsIgnoreCase("wiseregistrycleaner") || apiName.equalsIgnoreCase("wisediskcleaner");
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
		return StringUtils.removePattern(doc.select("a:contains(Direct Download Link)").attr("data-href"), "&onid=.*$");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		Elements versionElement = doc.select(".product-landing-quick-specs-row");
		if (!versionElement.isEmpty()) {
			return ParsingUtils.extractVersionNumberFromString(StringUtils.replacePattern(versionElement.select(".product-landing-quick-specs-row-content").get(0).text(), "(B|b)uild", "."));
		} else {
			return ParsingUtils.extractVersionNumberFromString(StringUtils.replacePattern(doc.select("tr#specsPubVersion").text(), "(B|b)uild", "."));
		}
	}

}
