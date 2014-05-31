package com.updapy.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.service.MailSenderService;
import com.updapy.service.RemoteService;

@Service
public class RemoteServiceImpl implements RemoteService {

	@Autowired
	MailSenderService mailSenderService;

	@Override
	public ApplicationVersion retrieveRemoteLatestVersion(ApplicationReference reference) {
		ApplicationVersion applicationVersion = new ApplicationVersion();
		Document doc = null;
		try {
			doc = Jsoup.connect(reference.getGlobalUrl()).get();
		} catch (Exception e) {
			mailSenderService.sendAdminConnectionError(reference.getGlobalUrl());
			return null;
		}
		applicationVersion.setReference(reference);
		applicationVersion.setVersionDate(new Date());
		applicationVersion.setVersionNumber(findVersionNumber(doc));
		applicationVersion.setWin32UrlEn(findWin32UrlEn(doc));
		applicationVersion.setWin32UrlFr(findWin32UrlFr(doc));
		applicationVersion.setWin64UrlEn(findWin64UrlEn(doc));
		applicationVersion.setWin64UrlFr(findWin64UrlFr(doc));

		if (StringUtils.isBlank(applicationVersion.getVersionNumber()) || StringUtils.isBlank(applicationVersion.getWin32UrlEn())) {
			// The version is not valid
			return null;
		}

		return applicationVersion;
	}

	private String findWin64UrlFr(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}

	private String findWin64UrlEn(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}

	private String findWin32UrlFr(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}

	private String findWin32UrlEn(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}

	private String findVersionNumber(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}

}
