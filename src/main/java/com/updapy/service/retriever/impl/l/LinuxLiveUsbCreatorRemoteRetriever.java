package com.updapy.service.retriever.impl.l;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.service.retriever.impl.BaseUrlRemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class LinuxLiveUsbCreatorRemoteRetriever implements RemoteRetriever, BaseUrlRemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://www.linuxliveusb.com/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("linuxliveusbcreator");
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
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, doc.select("div.dl-button").select("a").attr("href"));
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(doc.select("div.dl-button").select("span:containsOwn(Version)").text());
	}

	@Override
	public String getBaseUrl() {
		return ROOT_DOWNLOAD_WEBSITE;
	}

}
