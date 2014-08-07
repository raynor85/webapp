package com.updapy.service;

import java.util.List;
import java.util.Locale;

import com.updapy.form.model.NewVersion;
import com.updapy.form.model.UpdateUrl;
import com.updapy.model.ApplicationReference;
import com.updapy.model.Newsletter;

public interface EmailSenderService {

	int getNonPriorEmailsMaxSentPerDay();

	boolean sendActivationLink(String email, String key, Locale locale);

	boolean sendResetPasswordLink(String email, String key, Locale locale);

	boolean sendAdminConnectionError(String url);

	boolean sendAdminRetrieverError(String applicationName);

	boolean sendAdminRequestedApplication(String name, String url, Locale locale);

	boolean sendSingleUpdate(String email, NewVersion newVersion, List<UpdateUrl> otherUpdateUrls, Locale locale);

	boolean sendWeeklyUpdates(String email, List<NewVersion> newVersions, Locale locale);

	boolean sendAddedApplication(String email, List<ApplicationReference> applications, Locale locale);

	boolean sendDeletedApplication(String email, ApplicationReference application, Locale locale);

	boolean sendNewsletter(String email, Newsletter newsletter, Locale locale);

	boolean sendPersonalEmail(String email, String subject, String title, String message, Locale locale);

}
