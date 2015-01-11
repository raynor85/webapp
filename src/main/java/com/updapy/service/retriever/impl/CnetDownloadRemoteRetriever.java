package com.updapy.service.retriever.impl;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class CnetDownloadRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		String apiName = application.getApiName();
		return apiName.equalsIgnoreCase("avast") || apiName.equalsIgnoreCase("slimdrivers") || apiName.equalsIgnoreCase("skype") || apiName.equalsIgnoreCase("advancedsystemcare") || apiName.equalsIgnoreCase("faststoneimageviewer") || apiName.equalsIgnoreCase("ytdvideodownloader") || apiName.equalsIgnoreCase("adawarefree") || apiName.equalsIgnoreCase("gommediaplayer") || apiName.equalsIgnoreCase("smartdefrag") || apiName.equalsIgnoreCase("virtualdj") || apiName.equalsIgnoreCase("photoscape")
				|| apiName.equalsIgnoreCase("drivermax") || apiName.equalsIgnoreCase("primopdf") || apiName.equalsIgnoreCase("avgfreeantivirus") || apiName.equalsIgnoreCase("driverbooster") || apiName.equalsIgnoreCase("freeyoutubedownloader") || apiName.equalsIgnoreCase("objectdock");
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
		Elements versionElement = doc.select(".product-landing-quick-specs-row");
		if (!versionElement.isEmpty()) {
			return ParsingUtils.extractVersionNumberFromString(versionElement.select(".product-landing-quick-specs-row-content").get(0).text());
		} else {
			return ParsingUtils.extractVersionNumberFromString(doc.select("tr#specsPubVersion").text());
		}
	}

}
