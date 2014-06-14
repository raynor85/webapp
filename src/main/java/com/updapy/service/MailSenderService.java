package com.updapy.service;

import java.util.List;
import java.util.Locale;

import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.UpdateUrl;

public interface MailSenderService {

	boolean sendActivationLink(String email, String key, Locale locale);

	boolean sendResetPasswordLink(String email, String key, Locale locale);

	boolean sendAdminConnectionError(String url);

	boolean sendAdminRetrieverError(String applicationName);

	boolean sendAdminRequestedApplication(String name, String url);

	boolean sendSingleUpdate(String email, ApplicationReference application, ApplicationVersion newVersion, UpdateUrl updateUrl, List<UpdateUrl> otherUpdateUrls, Locale locale);

}
