package com.updapy.service.retriever.impl.n;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.HttpUtils;
import com.updapy.util.ParsingUtils;

@Component
public class NeroBurningRomRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("neroburningrom");
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
		return getJsonContent(doc).getString("dlLink");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		String version = getJsonContent(doc).getString("dlTitle");
		String[] date = ParsingUtils.selectFromPattern(version, "[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}").split("\\.");
		return date[2] + '.' + date[1] + '.' + date[0];
	}

	public JSONObject getJsonContent(Document doc) throws IOException {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Host", "www.nero.com");
		headers.put("Referer", "http://www.nero.com/eng/downloads/");
		String jsonContentString = HttpUtils.executeGetRequest(doc.baseUri(), headers);
		JSONObject jsonObject = new JSONObject(jsonContentString);
		return jsonObject.getJSONObject("data");
	}

}
