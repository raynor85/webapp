package com.updapy.service.retriever.impl.z;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class ZoneAlarmRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		String apiName = application.getApiName();
		return apiName.equalsIgnoreCase("zonealarmfirewall") || apiName.equalsIgnoreCase("zonealarmextremesecurity");
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
		return getDownloadLink(doc);
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(getDownloadLink(doc), ".*/").replace('_', '.'));
	}

	private String getDownloadLink(Document doc) {
		return doc.select("a:contains(download again)[href*=.exe]").attr("href");
	}
}
