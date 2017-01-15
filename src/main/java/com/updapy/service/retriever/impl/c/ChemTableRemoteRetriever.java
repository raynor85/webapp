package com.updapy.service.retriever.impl.c;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class ChemTableRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		String apiName = application.getApiName();
		return apiName.equalsIgnoreCase("regorganizer") || apiName.equalsIgnoreCase("registrylife");
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
		return ParsingUtils.addHttpsPrefix(doc.select("a:contains(Download)[href*=.exe]").attr("href"));
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(doc.select("h1:contains(Downloading)").text());
	}

}
