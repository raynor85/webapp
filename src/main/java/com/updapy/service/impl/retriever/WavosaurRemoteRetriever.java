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
public class WavosaurRemoteRetriever implements RemoteRetriever {

	private static final String FR_ROOT_DOWNLOAD_WEBSITE = "http://fr.wavosaur.com/";
	private static final String EN_ROOT_DOWNLOAD_WEBSITE = "http://www.wavosaur.com/";
	private static final String FR_DOWNLOAD_WEBSITE = FR_ROOT_DOWNLOAD_WEBSITE + "telecharger.php";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("wavosaur");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) {
		return getDownloadLinkFr("x64");
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) {
		return getDownloadLinkEn(doc, "x64");
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) {
		return getDownloadLinkFr("x86");
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		return getDownloadLinkEn(doc, "x86");
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(getDownloadLinkEn(doc, "x86"), "x86.*$"));
	}

	private String getDownloadLinkFr(String pattern) {
		try {
			Document docFr = RemoteServiceImpl.retrieveHtmlDocumentAgent32(FR_DOWNLOAD_WEBSITE);
			return FR_ROOT_DOWNLOAD_WEBSITE + docFr.select("a[href*=" + pattern + "]").attr("href");
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	private String getDownloadLinkEn(Document doc, String pattern) {
		return EN_ROOT_DOWNLOAD_WEBSITE + doc.select("a[href*=" + pattern + "]").attr("href");
	}

}
