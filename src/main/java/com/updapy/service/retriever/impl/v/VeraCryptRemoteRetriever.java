package com.updapy.service.retriever.impl.v;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class VeraCryptRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("veracrypt");
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
		String fileId = doc.select("a#download_button").attr("d:fileid");
		return doc.select("a[title*=for Windows]").attr("href").replaceFirst("&DownloadId=[0-9]+", "&DownloadId=" + fileId);
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		return ParsingUtils.extractVersionNumberFromString(convertCharToDigit(StringUtils.removePattern(doc.select("td:contains(VeraCrypt version)").first().text(), "VeraCrypt version")));
	}

	private String convertCharToDigit(String version) {
		Pattern pattern = Pattern.compile("[A-Za-z]");
		Matcher matcher = pattern.matcher(version);
		if (matcher.find()) {
			String versionWithoutLetters = matcher.replaceAll(Integer.toString(Character.getNumericValue(version.charAt(matcher.end() - 1)) - 9));
			return StringUtils.replaceChars(versionWithoutLetters, '-', '.');
		}
		return version;
	}

}
