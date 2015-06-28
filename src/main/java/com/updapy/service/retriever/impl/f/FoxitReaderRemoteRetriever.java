package com.updapy.service.retriever.impl.f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
		return "https://download.foxitsoftware.com/latest.php?product=Foxit-Reader&platform=Windows&package_type=exe&language=French";
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return "https://download.foxitsoftware.com/latest.php?product=Foxit-Reader&platform=Windows&package_type=exe&language=English";
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(getHighestVersion(doc.select("div.version_row").select("option")));
	}

	private String getHighestVersion(Elements versions) {
		List<String> versionLists = new ArrayList<String>();
		for (Element version : versions) {
			versionLists.add(version.text());
		}
		return getHighestVersion(versionLists);
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
