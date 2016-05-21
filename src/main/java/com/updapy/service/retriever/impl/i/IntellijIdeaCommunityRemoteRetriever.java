package com.updapy.service.retriever.impl.i;

import java.io.IOException;

import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class IntellijIdeaCommunityRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("intellijideacommunity");
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
		return new JSONObject(doc.select("body").text()).getJSONArray("IIC").getJSONObject(0).getJSONObject("downloads").getJSONObject("windows").getString("link");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(new JSONObject(doc.select("body").text()).getJSONArray("IIC").getJSONObject(0).getString("version"));
	}

}
