package com.updapy.service.retriever.impl.s;

import java.io.IOException;

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
public class SyncBackFreeRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("syncbackfree");
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
				if (StringUtils.containsIgnoreCase(node.getWholeData(), "Download SyncBackFree")) {
					script = node.getWholeData();
				}
			}
		}
		return ParsingUtils.selectFromPattern(script, "http.*.exe");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(doc.select("h3.subTitle:contains(Download SyncBackFree)").text());
	}

}
