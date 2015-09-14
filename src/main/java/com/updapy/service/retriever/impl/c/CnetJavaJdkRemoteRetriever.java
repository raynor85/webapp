package com.updapy.service.retriever.impl.c;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class CnetJavaJdkRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE_VERSION_32 = "http://download.cnet.com/Java-Development-Kit-32-bit/3000-2218_4-12091.html";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("javadk");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) throws IOException {
		return doc.baseUri().replace("/3000", "/3001");
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		String version64Bits = retrieveVersionNumber(doc);
		String version32Bits = retrieveVersionNumber(RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(ROOT_DOWNLOAD_WEBSITE_VERSION_32));
		if (version32Bits.equals(version64Bits)) {
			return ROOT_DOWNLOAD_WEBSITE_VERSION_32.replace("/3000", "/3001");
		}
		return null;
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		Elements versionElement = doc.select(".product-landing-quick-specs-row");
		if (!versionElement.isEmpty()) {
			return ParsingUtils.extractVersionNumberFromString(StringUtils.replacePattern(versionElement.select(".product-landing-quick-specs-row-content").get(0).text(), "(B|b)uild", "."));
		} else {
			return ParsingUtils.extractVersionNumberFromString(StringUtils.replacePattern(doc.select("tr#specsPubVersion").text(), "(U|u)pdate", "."));
		}
	}

}
