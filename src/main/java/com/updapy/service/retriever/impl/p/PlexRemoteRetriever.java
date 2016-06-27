package com.updapy.service.retriever.impl.p;

import java.io.IOException;

import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class PlexRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("plex");
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
		JSONObject jsonObject = new JSONObject(doc.text());
		return jsonObject.getJSONObject("computer").getJSONObject("Windows").getJSONArray("releases").getJSONObject(0).getString("url");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		JSONObject jsonObject = new JSONObject(doc.text());
		return ParsingUtils.extractVersionNumberFromString(jsonObject.getJSONObject("computer").getJSONObject("Windows").getString("version"));
	}

}
