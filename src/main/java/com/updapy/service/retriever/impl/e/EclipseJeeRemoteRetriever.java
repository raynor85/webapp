package com.updapy.service.retriever.impl.e;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class EclipseJeeRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://www.eclipse.org/downloads/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("eclipsejee");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) {
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, retrieveDirectLink(ParsingUtils.addHttpPrefix(doc.select("a.downloadLink:contains(Windows 64 Bit)[href*=jee]").attr("href"))));
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		return ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, retrieveDirectLink(ParsingUtils.addHttpPrefix(doc.select("a.downloadLink:contains(Windows 32 Bit)[href*=jee]").attr("href"))));
	}

	private String retrieveDirectLink(String dlLink) {
		try {
			return RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(dlLink).select("a:contains(Direct link to file)").attr("href");
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		Pattern pattern = Pattern.compile("\\(.*\\)");
		String version = doc.select("#descriptionText").text();
		Matcher matcher = pattern.matcher(version);
		if (matcher.find()) {
			version = version.substring(matcher.start(), matcher.end());
		}
		return ParsingUtils.extractVersionNumberFromString(version);
	}

}
