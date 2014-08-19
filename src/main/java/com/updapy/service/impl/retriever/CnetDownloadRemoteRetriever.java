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
		String apiName = application.getApiName();
		return apiName.equalsIgnoreCase("avast") || apiName.equalsIgnoreCase("slimdrivers") || apiName.equalsIgnoreCase("skype") || apiName.equalsIgnoreCase("advancedsystemcare") || apiName.equalsIgnoreCase("faststoneimageviewer") || apiName.equalsIgnoreCase("ytdvideodownloader") || apiName.equalsIgnoreCase("adawarefree") || apiName.equalsIgnoreCase("gommediaplayer") || apiName.equalsIgnoreCase("smartdefrag") || apiName.equalsIgnoreCase("virtualdj") || apiName.equalsIgnoreCase("photoscape")
				|| apiName.equalsIgnoreCase("drivermax") || apiName.equalsIgnoreCase("primopdf") || apiName.equalsIgnoreCase("avgfreeantivirus");
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
