package com.updapy.service.retriever.impl.o;

import java.io.IOException;
import java.net.URLDecoder;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.service.retriever.impl.BaseUrlRemoteRetriever;
import com.updapy.util.HttpUtils;
import com.updapy.util.ParsingUtils;

@Component
public class OperaRemoteRetriever implements RemoteRetriever, BaseUrlRemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://www.opera.com";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("opera");
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
		return getDownloadLink(doc);
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(HttpUtils.getFilenameFromUrl(getDownloadLink(doc)));
	}

	private String getDownloadLink(Document doc) throws IOException {
		return HttpUtils.getRedirectionUrl(ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, URLDecoder.decode(RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(doc.select("a:contains(Download the offline package)").attr("href")).select("a:contains(try again)").attr("href"), "UTF-8")));
	}

	@Override
	public String getBaseUrl() {
		return ROOT_DOWNLOAD_WEBSITE;
	}
}
