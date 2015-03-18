package com.updapy.service.retriever.impl.m;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class MakeHumanRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://www.makehuman.org";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("makehuman");
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
		String downloadPageLink = ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, getDownloadLinkElement(doc).attr("href"));
		try {
			return RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(downloadPageLink).select("a[href*=win32]").attr("href");
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(getDownloadLinkElement(doc).text());
	}

	private Elements getDownloadLinkElement(Document doc) {
		return doc.select("a#stable");
	}

}
