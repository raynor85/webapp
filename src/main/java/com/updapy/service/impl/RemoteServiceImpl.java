package com.updapy.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.service.MailSenderService;
import com.updapy.service.RemoteService;
import com.updapy.service.retriever.RemoteRetriever;

@Service
public class RemoteServiceImpl implements RemoteService {

	@Autowired
	private MailSenderService mailSenderService;

	@Autowired
	private List<RemoteRetriever> remoteRetrievers;

	@Override
	public ApplicationVersion retrieveRemoteLatestVersion(ApplicationReference reference) {
		Document doc = retrieveHtmlDocument(reference.getGlobalUrl());
		if (doc == null) {
			return null;
		}

		ApplicationVersion applicationVersion = new ApplicationVersion();
		applicationVersion.setReference(reference);
		applicationVersion.setVersionDate(new Date());

		retrieveInformation: {
			for (RemoteRetriever remoteRetriever : remoteRetrievers) {
				if (remoteRetriever.support(reference)) {
					applicationVersion.setVersionNumber(remoteRetriever.retrieveVersionNumber(doc));
					applicationVersion.setWin32UrlEn(remoteRetriever.retrieveWin32UrlEn(doc));
					applicationVersion.setWin32UrlFr(remoteRetriever.retrieveWin32UrlFr(doc));
					applicationVersion.setWin64UrlEn(remoteRetriever.retrieveWin64UrlEn(doc));
					applicationVersion.setWin64UrlFr(remoteRetriever.retrieveWin64UrlFr(doc));
					break retrieveInformation;
				}
			}
		}

		if (StringUtils.isBlank(applicationVersion.getVersionNumber()) || StringUtils.isBlank(applicationVersion.getWin32UrlEn()) || !applicationVersion.isValidVersionNumber()) {
			// remote version not valid
			mailSenderService.sendAdminRetrieverError(reference.getName());
			return null;
		}

		return applicationVersion;
	}

	private Document retrieveHtmlDocument(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).timeout(15 * 1000).get();
		} catch (Exception e) {
			mailSenderService.sendAdminConnectionError(url);
			return null;
		}
		return doc;
	}

}
