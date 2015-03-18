package com.updapy.service.retriever.impl.b;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class BalsamiqRemoteRetriever implements RemoteRetriever {

	private static final String DOWNLOAD_WEBSITE = "http://balsamiq.com/download/";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("balsamiq");
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
		try {
			Elements scriptTags = RemoteServiceImpl.retrieveHtmlDocumentAgentMozilla(DOWNLOAD_WEBSITE).getElementsByTag("script");
			String script = "";
			for (Element tag : scriptTags) {
				for (DataNode node : tag.dataNodes()) {
					if (StringUtils.containsIgnoreCase(node.getWholeData(), "durl_windows = '")) {
						script = node.getWholeData();
					}
				}
			}
			return StringUtils.replacePattern(ParsingUtils.selectFromPattern(ParsingUtils.selectFromPattern(script, "durl_windows = '.*'"), "http.*exe"), "'( |)\\+( |)data.version( |)\\+( |)'", retrieveVersionNumber(doc));
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(StringUtils.removePattern(doc.select("body").text(), "date.*$"), "^.*version"));
	}

}
