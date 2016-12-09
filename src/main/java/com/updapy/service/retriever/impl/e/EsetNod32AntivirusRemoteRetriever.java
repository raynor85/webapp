package com.updapy.service.retriever.impl.e;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.HttpUtils;
import com.updapy.util.ParsingUtils;

@Component
public class EsetNod32AntivirusRemoteRetriever implements RemoteRetriever {

	private static final String DOWNLOAD_WEBSITE = "https://www.eset.com/us/home/antivirus/download/?type=13554&tx_esetdownloads_ajax[product]=86&tx_esetdownloads_ajax[plugin_id]=70007";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("esetnod32antivirus");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) throws IOException {
		return getDownloadLink(DOWNLOAD_WEBSITE, "French - France", ".*64.*");
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) throws IOException {
		return getDownloadLink(DOWNLOAD_WEBSITE, "English - United States", ".*64.*");
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) throws IOException {
		return getDownloadLink(DOWNLOAD_WEBSITE, "French - France", ".*32.*");
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return getDownloadLink(DOWNLOAD_WEBSITE, "English - United States", ".*32.*");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		Document docXml = Jsoup.parse(doc.text(), "", Parser.xmlParser());
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(docXml.select("div#file-summary").select("p:contains(Version)").text(), "Size.*$"));
	}

	@SuppressWarnings("unchecked")
	private String getDownloadLink(String downloadWebsite, String languageName, String versionPattern) throws IOException {
		String jsonContentString = HttpUtils.executeGetRequest(downloadWebsite);
		JSONObject jsonObject = new JSONObject(jsonContentString);
		JSONObject jsonAvailableLanguages = jsonObject.getJSONObject("selections").getJSONObject("2").getJSONObject("options");
		String languageId = null;
		for (String key : jsonAvailableLanguages.keySet()) {
			if ((jsonAvailableLanguages.getString(key)).equalsIgnoreCase(languageName)) {
				languageId = key;
			}
		}
		Map<String, Object> installationFiles = (Map<String, Object>) jsonObject.getJSONObject("files").getJSONObject("installer").toMap();
		for (Entry<String, Object> entry : installationFiles.entrySet()) {
			String url = ((Map<String, String>) entry.getValue()).get("url").toString();
			if (languageId != null && languageId.equals(((Map<String, String>) entry.getValue()).get("language")) && url.matches(versionPattern)) {
				return url;
			}
		}
		return null;
	}
}
