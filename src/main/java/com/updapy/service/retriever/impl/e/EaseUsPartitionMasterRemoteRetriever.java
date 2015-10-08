package com.updapy.service.retriever.impl.e;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class EaseUsPartitionMasterRemoteRetriever implements RemoteRetriever {

	private static final String DOWNLOAD_WEBSITE = "http://www.easeus.com/download/epmf-download.html";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("easeuspartitionmaster");
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
		return RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(DOWNLOAD_WEBSITE).select("a:contains(Download)[href*=.exe][href*=free][href*=epm]").attr("href");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(ParsingUtils.selectFromPattern(doc.text(), "epm:'.*',").split(",")[0]);
	}
}
