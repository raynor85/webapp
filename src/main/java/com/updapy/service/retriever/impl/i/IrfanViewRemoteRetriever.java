package com.updapy.service.retriever.impl.i;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class IrfanViewRemoteRetriever implements RemoteRetriever {

	private static final String DOWNLOAD_WEBSITE = "http://download.cnet.com/IrfanView/3000-2192_4-10021962.html";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("irfanview");
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
		return DOWNLOAD_WEBSITE.replace("/3000", "/3001");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		Elements versionElement = doc.select(".product-landing-quick-specs-row");
		if (!versionElement.isEmpty()) {
			return ParsingUtils.extractVersionNumberFromString(versionElement.select(".product-landing-quick-specs-row-content").get(0).text());
		} else {
			return ParsingUtils.extractVersionNumberFromString(doc.select("tr#specsPubVersion").text());
		}
	}

}
