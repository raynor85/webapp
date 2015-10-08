package com.updapy.service.retriever.impl.e;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.HttpUtils;
import com.updapy.util.ParsingUtils;

@Component
public class EvernoteRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("evernote");
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
		return ParsingUtils.extractVersionNumberFromString(ParsingUtils.selectFromPattern(HttpUtils.getRedirectionUrl(getDownloadLink(doc)), "/Evernote.*exe"));
	}

	public String getDownloadLink(Document doc) throws IOException {
		return "https://evernote.com/download/get.php?file=Win";
	}

}
