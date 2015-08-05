package com.updapy.service.retriever.impl.e;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class EsetNod32AntivirusRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://download.eset.com/download/win/eav/";
	private static final String ROOT_DOWNLOAD_WEBSITE_VERSION_FR_32 = "http://www.eset.com/us/download/home/detail/family/2/language/FRA/operatingsystem/230/";
	private static final String ROOT_DOWNLOAD_WEBSITE_VERSION_FR_64 = "http://www.eset.com/us/download/home/detail/family/2/language/FRA/operatingsystem/231/";
	private static final String ROOT_DOWNLOAD_WEBSITE_VERSION_EN_32 = "http://www.eset.com/us/download/home/detail/family/2/language/ENU/operatingsystem/230/";
	private static final String ROOT_DOWNLOAD_WEBSITE_VERSION_EN_64 = "http://www.eset.com/us/download/home/detail/family/2/language/ENU/operatingsystem/231/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("esetnod32antivirus");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) throws IOException {
		return getDownloadLink(ROOT_DOWNLOAD_WEBSITE_VERSION_FR_64);
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) throws IOException {
		return getDownloadLink(ROOT_DOWNLOAD_WEBSITE_VERSION_EN_64);
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) throws IOException {
		return getDownloadLink(ROOT_DOWNLOAD_WEBSITE_VERSION_FR_32);
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return getDownloadLink(ROOT_DOWNLOAD_WEBSITE_VERSION_EN_32);
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		Document docXml = Jsoup.parse(doc.text(), "", Parser.xmlParser());
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(docXml.select("div#file-summary").select("p:contains(Version)").text(), "Size.*$"));
	}

	private String getDownloadLink(String downloadWebsite) throws IOException {
		Document doc = RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(downloadWebsite, 60 * 1000); // 1 minute because the website is slow...
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, StringUtils.removePattern(doc.select("div#file-summary").select("p:contains(File name)").text(), "^.*:\\s?").toLowerCase());
	}
}
