package com.updapy.service.impl.retriever;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class CnetDownloadRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		String applicationname = application.getName();
		return applicationname.equalsIgnoreCase("Avast! Free Antivirus") || applicationname.equalsIgnoreCase("SlimDrivers") || applicationname.equalsIgnoreCase("Skype") || applicationname.equalsIgnoreCase("Advanced SystemCare") || applicationname.equalsIgnoreCase("FastStone Image Viewer") || applicationname.equalsIgnoreCase("YTD Video Downloader") || applicationname.equalsIgnoreCase("Ad-Aware Free") || applicationname.equalsIgnoreCase("GOM Media Player")
				|| applicationname.equalsIgnoreCase("Smart Defrag") || applicationname.equalsIgnoreCase("Virtual DJ") || applicationname.equalsIgnoreCase("PhotoScape") || applicationname.equalsIgnoreCase("Driver Max") || applicationname.equalsIgnoreCase("PrimoPDF");
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
		return doc.baseUri().replace("/3000", "/3001");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(doc.select(".product-landing-quick-specs-row").select(".product-landing-quick-specs-row-content").get(0).text());
	}

}
