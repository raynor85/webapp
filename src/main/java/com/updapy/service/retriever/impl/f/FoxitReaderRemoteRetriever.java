package com.updapy.service.retriever.impl.f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.model.Version;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class FoxitReaderRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("foxitreader");
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
		return "http://www.foxitsoftware.com/downloads/latest.php?product=Foxit-Reader&platform=Windows&package_type=exe&Language=French";
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return "http://www.foxitsoftware.com/downloads/latest.php?product=Foxit-Reader&platform=Windows&package_type=exe&language=English";
	}

	@SuppressWarnings("unchecked")
	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		String jsonText = doc.text();
		JSONParser parser = new JSONParser();
		ContainerFactory containerFactory = new ContainerFactory() {
			@Override
			public List<String> creatArrayContainer() {
				return new ArrayList<String>();
			}

			@Override
			public Map<String, Object> createObjectContainer() {
				return new LinkedHashMap<String, Object>();
			}
		};
		try {
			Map<String, Object> json = (Map<String, Object>) parser.parse(jsonText, containerFactory);
			List<String> versions = ((List<String>) json.get("version"));
			return ParsingUtils.extractVersionNumberFromString(getHighestVersion(versions));
		} catch (ParseException pe) {
			return null;
		}

	}

	private String getHighestVersion(List<String> versions) {
		Version highestVersion = new Version(versions.get(0));
		for (String versionAsString : versions) {
			Version version = new Version(versionAsString);
			if (highestVersion.compareTo(version) == -1) {
				highestVersion = version;
			}
		}
		return highestVersion.get();
	}

}
