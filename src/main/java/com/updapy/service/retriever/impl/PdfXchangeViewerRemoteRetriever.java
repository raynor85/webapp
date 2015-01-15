package com.updapy.service.retriever.impl;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.model.Version;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class PdfXchangeViewerRemoteRetriever implements RemoteRetriever {

	private static final String VERSION_HISTORY_WEBSITE = "http://www.tracker-software.com/product/pdf-xchange-viewer";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("pdfxchangeviewer");
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
		String version = retrieveVersionNumber(doc);
		String[] subVersions = StringUtils.split(version, '.');
		String rebuildVersion = subVersions[0] + StringUtils.removePattern(subVersions[2], "^0*");
		return StringUtils.replacePattern(doc.select("item").first().select("enclosure").attr("url"), "[0-9]+", rebuildVersion);
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		String version1 = ParsingUtils.extractVersionNumberFromString(doc.select("item").first().select("title").text());
		String version2 = null;
		try {
			version2 = ParsingUtils.extractVersionNumberFromString(RemoteServiceImpl.retrieveHtmlDocumentAgent32(VERSION_HISTORY_WEBSITE).select("span.versionname").text());
		} catch (IOException e) {
			throw new RuntimeException();
		}
		if (version2 == null) {
			return version1;
		}
		if (new Version(version1).compareTo(new Version(version2)) == -1) {
			return version2;
		}
		return version1;
	}
}
