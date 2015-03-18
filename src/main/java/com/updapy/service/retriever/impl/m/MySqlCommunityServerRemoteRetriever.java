package com.updapy.service.retriever.impl.m;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class MySqlCommunityServerRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://dev.mysql.com/";
	private static final String SUFFIX_DOWNLOAD_WEBSITE = "?tpl=files&os=3";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("mysqlcommunityserver");
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
		return getDownloadLink(doc, "32-bit");
	}

	private String getDownloadLink(Document doc, String platform) {
		try {
			String dlLink = ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, getDocumentMsi(doc).select("td:contains(" + platform + ")").parents().select("td.col5").select("a").get(1).attr("href"));
			return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(dlLink).select("a:contains(No thanks, just start my download.)").attr("href"));
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(getDocumentMsi(doc).select("td.col3").first().text());
	}

	private Document getDocumentMsi(Document doc) {
		try {
			return RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(ParsingUtils.buildUrl(ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, doc.select("a").first().attr("href")), SUFFIX_DOWNLOAD_WEBSITE));
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

}
