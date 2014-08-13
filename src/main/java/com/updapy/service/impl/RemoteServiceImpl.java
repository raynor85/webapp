package com.updapy.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.service.EmailSenderService;
import com.updapy.service.RemoteService;
import com.updapy.service.retriever.RemoteRetriever;

@Service
public class RemoteServiceImpl implements RemoteService {

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private List<RemoteRetriever> remoteRetrievers;

	@Override
	public ApplicationVersion retrieveRemoteLatestVersion(ApplicationReference application) {
		Document doc = retrieveHtmlDocument(application);
		if (doc == null) {
			return null;
		}

		ApplicationVersion version = new ApplicationVersion();
		version.setApplication(application);
		version.setVersionDate(new Date());

		try {
			retrieveInformation: {
				for (RemoteRetriever remoteRetriever : remoteRetrievers) {
					if (remoteRetriever.support(application)) {
						version.setVersionNumber(remoteRetriever.retrieveVersionNumber(doc));
						version.setWin32UrlEn(remoteRetriever.retrieveWin32UrlEn(doc));
						version.setWin32UrlFr(remoteRetriever.retrieveWin32UrlFr(doc));
						version.setWin64UrlEn(remoteRetriever.retrieveWin64UrlEn(doc));
						version.setWin64UrlFr(remoteRetriever.retrieveWin64UrlFr(doc));
						break retrieveInformation;
					}
				}
			}
		} catch (Exception e) {
			emailSenderService.sendAdminRetrieverError(application.getName() + " <exception>");
			return null;
		}

		if (StringUtils.isBlank(version.getVersionNumber()) || StringUtils.isBlank(version.getWin32UrlEn()) || !version.isValidVersionNumber() || !isUrlValid(version.getWin32UrlEn()) || !isUrlValid(version.getWin32UrlFr()) || !isUrlValid(version.getWin64UrlEn()) || !isUrlValid(version.getWin64UrlFr())) {
			// remote version not valid
			emailSenderService.sendAdminRetrieverError(application.getName());
			return null;
		}

		return version;
	}

	private boolean isUrlValid(String url) {
		if (StringUtils.isBlank(url)) {
			// empty url is fine
			return true;
		}
		try {
			new URL(url);
		} catch (MalformedURLException e) {
			return false;
		}
		return true;
	}

	private Document retrieveHtmlDocument(ApplicationReference application) {
		Document doc = null;
		String url = application.getGlobalUrl();
		try {
			doc = retrieveHtmlDocumentAgent64(url);
		} catch (Exception e) {
			try {
				// let's try a second time, network can be unreliable sometimes!
				doc = retrieveHtmlDocumentAgent64(url);
			} catch (IOException e1) {
				// seems there is really a problem
				emailSenderService.sendAdminConnectionError(url);
				return null;
			}
		}
		return doc;
	}

	public static Document retrieveHtmlDocumentAgent64(String url) throws IOException {
		return Jsoup.connect(url).ignoreContentType(true).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").referrer("http://www.google.com").timeout(15 * 1000).followRedirects(true).get();
	}

	public static Document retrieveHtmlDocumentAgent32(String url) throws IOException {
		return Jsoup.connect(url).ignoreContentType(true).userAgent("Mozilla/5.0 (Windows NT 6.1; Win32; rv:25.0) Gecko/20100101 Firefox/25.0").referrer("http://www.google.com").timeout(15 * 1000).followRedirects(true).get();
	}

}
