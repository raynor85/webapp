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
	public ApplicationVersion retrieveRemoteLatestVersion(ApplicationReference application) {
		Document doc = retrieveHtmlDocument(application.getGlobalUrl());
		if (doc == null) {
			return null;
		}

		ApplicationVersion version = new ApplicationVersion();
		version.setApplication(application);
		version.setVersionDate(new Date());

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

		if (StringUtils.isBlank(version.getVersionNumber()) || StringUtils.isBlank(version.getWin32UrlEn()) || !version.isValidVersionNumber()) {
			// remote version not valid
			mailSenderService.sendAdminRetrieverError(application.getName());
			return null;
		}

		return version;
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
