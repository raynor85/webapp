package com.updapy.service.retriever.impl.c;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
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
		return apiName.equalsIgnoreCase("avast") || apiName.equalsIgnoreCase("slimdrivers") || apiName.equalsIgnoreCase("skype") || apiName.equalsIgnoreCase("advancedsystemcare") || apiName.equalsIgnoreCase("ytdvideodownloader") || apiName.equalsIgnoreCase("adawarefree") || apiName.equalsIgnoreCase("gommediaplayer") || apiName.equalsIgnoreCase("smartdefrag") || apiName.equalsIgnoreCase("virtualdj") || apiName.equalsIgnoreCase("photoscape")
				|| apiName.equalsIgnoreCase("drivermax") || apiName.equalsIgnoreCase("primopdf") || apiName.equalsIgnoreCase("avgfreeantivirus") || apiName.equalsIgnoreCase("driverbooster") || apiName.equalsIgnoreCase("freeyoutubedownloader") || apiName.equalsIgnoreCase("objectdock") || apiName.equalsIgnoreCase("drwebcureit") || apiName.equalsIgnoreCase("spywareblaster") || apiName.equalsIgnoreCase("slimcleaner") || apiName.equalsIgnoreCase("malwarefighter")
				|| apiName.equalsIgnoreCase("spywareterminator") || apiName.equalsIgnoreCase("wisecare365") || apiName.equalsIgnoreCase("pandafreeantivirus") || apiName.equalsIgnoreCase("iobituninstaller") || apiName.equalsIgnoreCase("kmplayer") || apiName.equalsIgnoreCase("fences") || apiName.equalsIgnoreCase("adfender") || apiName.equalsIgnoreCase("zonerphotostudio");
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
		return doc.baseUri().replace("/3000", "/3001");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		Elements versionElement = doc.select(".product-landing-quick-specs-row");
		if (!versionElement.isEmpty()) {
			return ParsingUtils.extractVersionNumberFromString(StringUtils.replacePattern(versionElement.select(".product-landing-quick-specs-row-content").get(0).text(), "(B|b)uild", "."));
		} else {
			return ParsingUtils.extractVersionNumberFromString(StringUtils.replacePattern(doc.select("tr#specsPubVersion").text(), "(B|b)uild", "."));
		}
	}

}
