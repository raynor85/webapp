package com.updapy.service.retriever.impl.u;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.service.retriever.impl.BaseUrlRemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class UltraCopierRemoteRetriever implements RemoteRetriever, BaseUrlRemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://ultracopier.first-world.info/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("ultracopier");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) throws IOException {
		return getDownloadLink(doc, "Ultracopier Free 64Bits");
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return getDownloadLink(doc, "Ultracopier Free 32Bits");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(ParsingUtils.selectFromPattern(doc.select("a:contains(Download ultracopier)").first().parent().text(), "Version.*"));
	}

	private String getDownloadLink(Document doc, String filter) throws IOException {
		return RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(getDownloadWebsite(doc)).select("a:contains(" + filter + ")").attr("href");
	}

	private String getDownloadWebsite(Document doc) {
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, doc.select("a:contains(Download ultracopier)").attr("href"));
	}

	@Override
	public String getBaseUrl() {
		return ROOT_DOWNLOAD_WEBSITE;
	}

}
