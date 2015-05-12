package com.updapy.service.retriever.impl.p;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.HttpUtils;
import com.updapy.util.ParsingUtils;

@Component
public class PdfCreatorRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("pdfcreator");
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
		String dlLink = doc.select(".pdf-select-footer").first().select("a:contains(Download)").attr("href");
		return HttpUtils.getRedirectionUrl(ParsingUtils.buildUrl(dlLink, RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(dlLink).select("a:contains(direct link)").attr("href")));
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(ParsingUtils.selectFromPattern(retrieveWin32UrlEn(doc), "PDF.*.exe").replace('_', '.'));
	}

}
