package com.updapy.service.retriever.impl.a;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class AdobeReaderDcRemoteRetriever implements RemoteRetriever {

	private static final String PATTERN_VERSION = "{version}";
	private static final String PATTERN_LANG = "{language}";
	private static final String LANG_EN = "en_US";
	private static final String LANG_FR = "fr_FR";

	private static final String DOWNLOAD_LINK = "ftp://ftp.adobe.com/pub/adobe/reader/win/AcrobatDC/" + PATTERN_VERSION + "/AcroRdrDC" + PATTERN_VERSION + "_" + PATTERN_LANG + ".exe";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("adobereaderdc");
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
		return StringUtils.replace(StringUtils.replace(DOWNLOAD_LINK, PATTERN_VERSION, getFormattedVersionNumber(doc)), PATTERN_LANG, LANG_FR);
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return StringUtils.replace(StringUtils.replace(DOWNLOAD_LINK, PATTERN_VERSION, getFormattedVersionNumber(doc)), PATTERN_LANG, LANG_EN);
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(doc.select("p#AUTO_ID_columnleft_p_version").text());
	}

	public String getFormattedVersionNumber(Document doc) throws IOException {
		return StringUtils.removeStart(StringUtils.remove(retrieveVersionNumber(doc), '.'), "20");
	}

}
