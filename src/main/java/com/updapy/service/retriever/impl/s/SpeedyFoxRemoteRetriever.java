package com.updapy.service.retriever.impl.s;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class SpeedyFoxRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://www.crystalidea.com";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("speedyfox");
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
		Elements scriptTags = doc.getElementsByTag("script");
		String script = "";
		for (Element tag : scriptTags) {
			for (DataNode node : tag.dataNodes()) {
				if (StringUtils.containsIgnoreCase(node.getWholeData(), "zip")) {
					script = node.getWholeData();
				}
			}
		}
		String url = ParsingUtils.buildUrl(ROOT_DOWNLOAD_WEBSITE, StringUtils.removePattern(StringUtils.removePattern(script, "^.*href=.\""), ".\".*$"));
		if (StringUtils.containsIgnoreCase(url, "mac")) {
			return null;
		}
		return url;
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		Pattern pattern = Pattern.compile("\\(.*\\)");
		String version = doc.select("p:contains(Download)").text();
		Matcher matcher = pattern.matcher(version);
		if (matcher.find()) {
			version = version.substring(matcher.start(), matcher.end());
		}
		return ParsingUtils.extractVersionNumberFromString(version);
	}

}
