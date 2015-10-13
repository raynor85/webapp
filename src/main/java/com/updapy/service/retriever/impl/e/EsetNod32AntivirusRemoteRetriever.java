package com.updapy.service.retriever.impl.e;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
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

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://www.eset.com/us/support/download/home/nod32-antivirus/?type=13554&tx_esetdownloads_ajax[product]=3";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("esetnod32antivirus");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) throws IOException {
		return getDownloadLink(ROOT_DOWNLOAD_WEBSITE, "FR_FR", ".*64.*");
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) throws IOException {
		return getDownloadLink(ROOT_DOWNLOAD_WEBSITE, "EN_US", ".*64.*");
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) throws IOException {
		return getDownloadLink(ROOT_DOWNLOAD_WEBSITE, "FR_FR", ".*32.*");
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return getDownloadLink(ROOT_DOWNLOAD_WEBSITE, "EN_US", ".*32.*");
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		Document docXml = Jsoup.parse(doc.text(), "", Parser.xmlParser());
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(docXml.select("div#file-summary").select("p:contains(Version)").text(), "Size.*$"));
	}

	private String getDownloadLink(String downloadWebsite, String languageCode, String versionPattern) throws IOException {
		String jsonContentString = HttpUtils.executeGetRequest(downloadWebsite);
		JSONObject jsonObject = new JSONObject(jsonContentString);
		JSONObject jsonAvailableLanguages = jsonObject.getJSONObject("availableLanguages");
		Integer languageId = null;
		for (int i = 1; i < jsonAvailableLanguages.length(); i++) {
			try {
				JSONObject jsonLanguage = (JSONObject) jsonAvailableLanguages.get(i + "");
				if (((String) jsonLanguage.get("code")).equalsIgnoreCase(languageCode)) {
					languageId = i;
				}
			} catch (JSONException e) {
				// ignore
			}
		}
		JSONArray jsonInstallationFiles = jsonObject.getJSONArray("installationFiles");
		for (Object jsonInstallationFile : jsonInstallationFiles) {
			String url = ((JSONObject) jsonInstallationFile).getString("file");
			if (languageId == ((JSONObject) jsonInstallationFile).getInt("language") && url.matches(versionPattern)) {
				return url;
			}
		}
		return null;
	}
}
