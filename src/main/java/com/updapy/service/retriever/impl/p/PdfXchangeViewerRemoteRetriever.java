package com.updapy.service.retriever.impl.p;

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

	private static final String ROOT_WEBSITE = "http://www.tracker-software.com";
	private static final String VERSION_HISTORY_WEBSITE = ROOT_WEBSITE + "/product/pdf-xchange-viewer";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("pdfxchangeviewer");
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
		String version = retrieveVersionNumber(doc);
		String[] subVersions = StringUtils.split(version, '.');
		String rebuildVersion = subVersions[0] + StringUtils.removePattern(subVersions[2], "^0*");
		String url = StringUtils.replacePattern(doc.select("item").first().select("enclosure").attr("url"), "[0-9]+", rebuildVersion);
		if (url.contains(ROOT_WEBSITE + "/")) {
			return url;
		} else {
			return url.replace(ROOT_WEBSITE, ROOT_WEBSITE + "/");
		}
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		String version1 = ParsingUtils.extractVersionNumberFromString(doc.select("item").first().select("title").text());
		String version2 = ParsingUtils.extractVersionNumberFromString(RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(VERSION_HISTORY_WEBSITE).select("span.versionname").text());
		if (version2 == null) {
			return version1;
		}
		if (new Version(version1).compareTo(new Version(version2)) == -1) {
			return version2;
		}
		return version1;
	}
}
