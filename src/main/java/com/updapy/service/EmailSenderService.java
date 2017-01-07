package com.updapy.service;

import java.util.List;
import java.util.Locale;

import org.springframework.transaction.annotation.Transactional;

import com.updapy.form.model.enumeration.SubjectMessage;
import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.Newsletter;
import com.updapy.model.enumeration.TypeRetrievalError;

@Transactional
public interface EmailSenderService {

	int getNonPriorEmailsMaxSentPerDay();

	boolean sendActivationLink(String email, String key, Locale locale);

	boolean sendResetPasswordLink(String email, String key, Locale locale);

	boolean sendSingleUpdate(String email, String key, ApplicationVersion newVersion, Locale locale);

	boolean sendWeeklyUpdates(String email, String key, List<ApplicationVersion> newVersions, Locale locale);

	boolean sendAddedApplication(String email, List<ApplicationReference> applications, Locale locale);

	boolean sendDeletedApplication(String email, ApplicationReference application, Locale locale);

	boolean sendNewsletter(String email, Newsletter newsletter, Locale locale);

	boolean sendPersonalEmail(String email, String subject, String title, String message, Locale locale);

	boolean sendAdminConnectionError(String url);

	boolean sendAdminRetrieverError(String applicationName, TypeRetrievalError typeRetrievalError);

	boolean sendAdminRequestedApplication(String email, String name, String url, Locale locale);

	boolean sendAdminMessage(String email, SubjectMessage subject, String text, boolean anonymous);

}
