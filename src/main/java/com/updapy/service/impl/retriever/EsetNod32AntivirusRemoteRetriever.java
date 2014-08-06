package com.updapy.service.impl.retriever;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class EsetNod32AntivirusRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://download.eset.com/download/win/eav/";
	private static final String ROOT_DOWNLOAD_WEBSITE_VERSION_FR_32 = "http://www.eset.com/us/download/home/detail/family/2/language/FRA/operatingsystem/136/";
	private static final String ROOT_DOWNLOAD_WEBSITE_VERSION_FR_64 = "http://www.eset.com/us/download/home/detail/family/2/language/FRA/operatingsystem/137/";
	private static final String ROOT_DOWNLOAD_WEBSITE_VERSION_EN_32 = "http://www.eset.com/us/download/home/detail/family/2/language/ENU/operatingsystem/136/";
	private static final String ROOT_DOWNLOAD_WEBSITE_VERSION_EN_64 = "http://www.eset.com/us/download/home/detail/family/2/language/ENU/operatingsystem/137/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getName().equalsIgnoreCase("ESET NOD32 Antivirus");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) {
		return getDownloadLink(ROOT_DOWNLOAD_WEBSITE_VERSION_FR_64);
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) {
		return getDownloadLink(ROOT_DOWNLOAD_WEBSITE_VERSION_EN_64);
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) {
		return getDownloadLink(ROOT_DOWNLOAD_WEBSITE_VERSION_FR_32);
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		return getDownloadLink(ROOT_DOWNLOAD_WEBSITE_VERSION_EN_32);
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(doc.select("div.changelog").select("h3").get(0).text());
	}

	private String getDownloadLink(String downloadWebsite) {
		try {
			Document doc = RemoteServiceImpl.retrieveHtmlDocumentAgent32(downloadWebsite);
			return ROOT_DOWNLOAD_WEBSITE + StringUtils.removePattern(doc.select("div#file-summary").select("p:contains(File name)").text(), "^.*:\\s?");
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
}
