package com.updapy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.updapy.form.model.NewVersion;
import com.updapy.form.model.UpdateUrl;
import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.EmailSingleUpdate;
import com.updapy.model.User;
import com.updapy.model.enumeration.Lang;
import com.updapy.model.enumeration.OsVersion;
import com.updapy.repository.EmailSingleUpdateRepository;
import com.updapy.service.EmailSenderService;
import com.updapy.service.EmailSingleUpdateService;
import com.updapy.service.UserService;

@Service
public class EmailSingleUpdateServiceImpl implements EmailSingleUpdateService {

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailSingleUpdateRepository emailSingleUpdateRepository;

	@Override
	public EmailSingleUpdate addEmailSingleUpdate(User user, ApplicationVersion version) {
		removeCurrentEmailSingleUpdatesForApplication(user, version.getApplication());
		EmailSingleUpdate emailSingleUpdate = new EmailSingleUpdate();
		emailSingleUpdate.setUser(user);
		emailSingleUpdate.setVersion(version);
		emailSingleUpdate.setSent(false);
		return save(emailSingleUpdate);
	}

	private void removeCurrentEmailSingleUpdatesForApplication(User user, ApplicationReference application) {
		List<EmailSingleUpdate> emailSingleUpdates = getEmailSingleUpdates(user, application);
		if (emailSingleUpdates != null && !emailSingleUpdates.isEmpty()) {
			deleteEmailSingleUpdates(emailSingleUpdates);
		}
	}

	private List<EmailSingleUpdate> getEmailSingleUpdates(User user, ApplicationReference application) {
		return emailSingleUpdateRepository.findByUserAndVersionApplicationAndSentFalse(user, application);
	}

	private void deleteEmailSingleUpdates(List<EmailSingleUpdate> emailSingleUpdates) {
		emailSingleUpdateRepository.delete(emailSingleUpdates);
	}

	@Override
	public int sendEmailSingleUpdates() {
		List<EmailSingleUpdate> emailSingleUpdates = emailSingleUpdateRepository.findBySentFalse(new PageRequest(0, emailSenderService.getNonPriorEmailsMaxSentPerDay()));
		int count = 0;
		for (EmailSingleUpdate emailSingleUpdate : emailSingleUpdates) {
			User user = emailSingleUpdate.getUser();
			ApplicationVersion newVersion = emailSingleUpdate.getVersion();
			UpdateUrl updateUrl = userService.getDownloadUrlMatchingSettings(user, newVersion);
			boolean hasBeenSent = emailSenderService.sendSingleUpdate(user.getEmail(), new NewVersion(newVersion.getApplication().getName(), newVersion.getVersionNumber(), updateUrl), getOtherDownloadUrls(updateUrl, newVersion), user.getLangEmail());
			if (hasBeenSent) {
				emailSingleUpdate.setSent(true);
				save(emailSingleUpdate);
				count++;
			}
		}
		return count;
	}

	private EmailSingleUpdate save(EmailSingleUpdate emailSingleUpdate) {
		return emailSingleUpdateRepository.saveAndFlush(emailSingleUpdate);
	}

	private List<UpdateUrl> getOtherDownloadUrls(UpdateUrl defaultUpdateUrl, ApplicationVersion version) {
		List<UpdateUrl> otherUrls = new ArrayList<UpdateUrl>();
		Lang defaultLang = defaultUpdateUrl.getLang();
		OsVersion defaultOsVersion = defaultUpdateUrl.getOsVersion();
		if (Lang.en.equals(defaultLang)) {
			if (OsVersion.WIN_32_BITS.equals(defaultOsVersion)) {
				if (StringUtils.isNotBlank(version.getWin64UrlEn())) {
					otherUrls.add(new UpdateUrl(version.getWin64UrlEn(), Lang.en, OsVersion.WIN_64_BITS));
				}
				if (StringUtils.isNotBlank(version.getWin32UrlFr())) {
					otherUrls.add(new UpdateUrl(version.getWin32UrlFr(), Lang.fr, OsVersion.WIN_32_BITS));
				}
				if (StringUtils.isNotBlank(version.getWin64UrlFr())) {
					otherUrls.add(new UpdateUrl(version.getWin64UrlFr(), Lang.fr, OsVersion.WIN_64_BITS));
				}
			}
			if (OsVersion.WIN_64_BITS.equals(defaultOsVersion)) {
				if (StringUtils.isNotBlank(version.getWin32UrlEn())) {
					otherUrls.add(new UpdateUrl(version.getWin32UrlEn(), Lang.en, OsVersion.WIN_32_BITS));
				}
				if (StringUtils.isNotBlank(version.getWin32UrlFr())) {
					otherUrls.add(new UpdateUrl(version.getWin32UrlFr(), Lang.fr, OsVersion.WIN_32_BITS));
				}
				if (StringUtils.isNotBlank(version.getWin64UrlFr())) {
					otherUrls.add(new UpdateUrl(version.getWin64UrlFr(), Lang.fr, OsVersion.WIN_64_BITS));
				}
			}
		}
		if (Lang.fr.equals(defaultLang)) {
			if (OsVersion.WIN_32_BITS.equals(defaultOsVersion)) {
				if (StringUtils.isNotBlank(version.getWin64UrlFr())) {
					otherUrls.add(new UpdateUrl(version.getWin64UrlFr(), Lang.fr, OsVersion.WIN_64_BITS));
				}
				if (StringUtils.isNotBlank(version.getWin32UrlEn())) {
					otherUrls.add(new UpdateUrl(version.getWin32UrlEn(), Lang.en, OsVersion.WIN_32_BITS));
				}
				if (StringUtils.isNotBlank(version.getWin64UrlEn())) {
					otherUrls.add(new UpdateUrl(version.getWin64UrlEn(), Lang.en, OsVersion.WIN_64_BITS));
				}
			}
			if (OsVersion.WIN_64_BITS.equals(defaultOsVersion)) {
				if (StringUtils.isNotBlank(version.getWin32UrlFr())) {
					otherUrls.add(new UpdateUrl(version.getWin32UrlFr(), Lang.fr, OsVersion.WIN_32_BITS));
				}
				if (StringUtils.isNotBlank(version.getWin32UrlEn())) {
					otherUrls.add(new UpdateUrl(version.getWin32UrlEn(), Lang.en, OsVersion.WIN_32_BITS));
				}
				if (StringUtils.isNotBlank(version.getWin64UrlEn())) {
					otherUrls.add(new UpdateUrl(version.getWin64UrlEn(), Lang.en, OsVersion.WIN_64_BITS));
				}
			}
		}
		return otherUrls;
	}

}
