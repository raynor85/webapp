package com.updapy.service;

import java.util.List;
import java.util.Locale;

import com.updapy.form.model.NewVersion;
import com.updapy.form.model.UpdateUrl;

public interface MailSenderService {

	boolean sendActivationLink(String email, String key, Locale locale);

	boolean sendResetPasswordLink(String email, String key, Locale locale);

	boolean sendAdminConnectionError(String url);

	boolean sendAdminRetrieverError(String applicationName);

	boolean sendAdminRequestedApplication(String name, String url, Locale locale);

	boolean sendSingleUpdate(String email, NewVersion newVersion, List<UpdateUrl> otherUpdateUrls, Locale locale);

	boolean sendWeeklyUpdates(String email, List<NewVersion> newVersions, Locale locale);

}
