package com.updapy.service.retriever.impl.j;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class Jv16PowerToolsXRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("jv16powertoolsx");
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
		return "https://www.macecraft.com/downloads/jv16pt_setup.exe";
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(ParsingUtils.selectFromPattern(doc.select("p:contains(Product version)").text(), "Product version.*,").split(",")[0]);
	}

}
