package com.updapy.service.retriever.impl.o;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class OpenOfficeRemoteRetriever implements RemoteRetriever {

	private static final String PATTERN_VERSION = "{version}";
	private static final String PATTERN_LANG = "{lang}";
	private static final String DOWNLOAD_WEBSITE = "http://sourceforge.net/projects/openofficeorg.mirror/files/" + PATTERN_VERSION + "/binaries/" + PATTERN_LANG + "/Apache_OpenOffice_" + PATTERN_VERSION + "_Win_x86_install_" + PATTERN_LANG + ".exe/download";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("openoffice");
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
		return StringUtils.replace(StringUtils.replace(DOWNLOAD_WEBSITE, PATTERN_VERSION, getVersionNumber(doc)), PATTERN_LANG, "fr");
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return StringUtils.replace(StringUtils.replace(DOWNLOAD_WEBSITE, PATTERN_VERSION, getVersionNumber(doc)), PATTERN_LANG, "en-US");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return getVersionNumber(doc);
	}

	private String getVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(StringUtils.removePattern(doc.select("body").text(), "Release version in.*$"), "^.*DL.VERSION"));
	}

}
