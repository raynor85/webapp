package com.updapy.service.retriever.impl.k;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.service.retriever.impl.BaseUrlRemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class KmPlayerRemoteRetriever implements RemoteRetriever, BaseUrlRemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://cdn.kmplayer.com";
	private static final String FILE_EXTENSION = ".exe";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("kmplayer");
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
		return ROOT_DOWNLOAD_WEBSITE + StringUtils.removePattern(StringUtils.removePattern(doc.select("body").text(), FILE_EXTENSION + ".*$"), "^.*" + ROOT_DOWNLOAD_WEBSITE) + FILE_EXTENSION;
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(StringUtils.removePattern(doc.select("body").text(), FILE_EXTENSION + ".*$"), "^.*KMPlayer_"));
	}

	@Override
	public String getBaseUrl() {
		return ROOT_DOWNLOAD_WEBSITE + FILE_EXTENSION;
	}

}
