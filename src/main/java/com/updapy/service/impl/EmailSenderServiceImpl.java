package com.updapy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.updapy.form.model.NewVersion;
import com.updapy.form.model.UpdateUrl;
import com.updapy.service.EmailCounterService;
import com.updapy.service.EmailSenderService;
import com.updapy.util.MessageUtils;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

	private Logger log = LoggerFactory.getLogger(EmailSenderServiceImpl.class);

	@Autowired
	private EmailCounterService emailCounterService;

	@Autowired
	private MessageUtils messageUtils;

	@Autowired
	private VelocityEngine velocityEngine;

	// public static final int MAX_NO_PRIOR_EMAILS_SENT_PER_DAY = 150; // let 50 emails per day for subscriptions or other prior emails

	private static final String ADMIN_EMAIL = System.getenv("ADMIN_EMAIL");
	private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
	private static final String SMTP_AUTH_USER = System.getenv("SENDGRID_USERNAME");
	private static final String SMTP_AUTH_PWD = System.getenv("SENDGRID_PASSWORD");

	@Override
	public int getNonPriorEmailsMaxSentPerDay() {
		return Integer.parseInt(messageUtils.getSimpleMessage("email.send.max"));
	}

	@Override
	public boolean sendActivationLink(String email, String key, Locale locale) {
		String link = messageUtils.getSimpleMessage("application.root.url", locale) + "/user/activate?email=" + email + "&key=" + key;
		String fromEmail = messageUtils.getSimpleMessage("email.noreply", locale);
		String subject = messageUtils.getSimpleMessage("email.activate.subject", locale);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("lang", messageUtils.getSimpleMessage("email.lang", locale));
		model.put("title", messageUtils.getSimpleMessage("email.activate.content.title", locale));
		model.put("text1", messageUtils.getSimpleMessage("email.activate.content.text1", locale));
		model.put("button", messageUtils.getSimpleMessage("email.activate.content.button", locale));
		model.put("text2", messageUtils.getSimpleMessage("email.activate.content.text2", locale));
		model.put("link", link);
		model.put("text3", "");
		model.put("follow1", messageUtils.getSimpleMessage("email.follow.part1", locale));
		model.put("follow2", messageUtils.getSimpleMessage("email.follow.part2", locale));
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "reset-activate-account.vm", "UTF-8", model);

		try {
			send(fromEmail, email, subject, message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean sendResetPasswordLink(String email, String key, Locale locale) {
		String link = messageUtils.getSimpleMessage("application.root.url", locale) + "/user/reset/password?email=" + email + "&key=" + key;
		String fromEmail = messageUtils.getSimpleMessage("email.noreply", locale);
		String subject = messageUtils.getSimpleMessage("email.reset.subject", locale);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("lang", messageUtils.getSimpleMessage("email.lang", locale));
		model.put("title", messageUtils.getSimpleMessage("email.reset.content.title", locale));
		model.put("text1", messageUtils.getSimpleMessage("email.reset.content.text1", locale));
		model.put("button", messageUtils.getSimpleMessage("email.reset.content.button", locale));
		model.put("text2", messageUtils.getSimpleMessage("email.reset.content.text2", locale));
		model.put("link", link);
		model.put("text3", messageUtils.getSimpleMessage("email.reset.content.text3", locale));
		model.put("follow1", messageUtils.getSimpleMessage("email.follow.part1", locale));
		model.put("follow2", messageUtils.getSimpleMessage("email.follow.part2", locale));
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "reset-activate-account.vm", "UTF-8", model);

		try {
			send(fromEmail, email, subject, message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private void send(String fromEmail, String toEmail, String subject, String htmlContent) throws Exception {
		if (messageUtils.getSimpleMessage("environnement").equals("dev")) {
			// skip email sending
			log.info("Sending email to '{}' with subject '{}' and content '{}'", toEmail, subject, htmlContent);
			emailCounterService.incCounter();
			return;
		}
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", "true");

		Authenticator auth = new SMTPAuthenticator();
		Session mailSession = Session.getDefaultInstance(props, auth);
		// uncomment for debugging infos to stdout
		// mailSession.setDebug(true);
		Transport transport = mailSession.getTransport();

		MimeMessage message = new MimeMessage(mailSession);

		Multipart multipart = new MimeMultipart("alternative");

		BodyPart bodyPart = new MimeBodyPart();
		bodyPart.setContent(htmlContent, "text/html");
		multipart.addBodyPart(bodyPart);

		message.setContent(multipart);
		message.setFrom(new InternetAddress(fromEmail, messageUtils.getSimpleMessage("application.name")));
		message.setSubject(subject);
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

		transport.connect();
		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		transport.close();

		emailCounterService.incCounter();
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = SMTP_AUTH_USER;
			String password = SMTP_AUTH_PWD;
			return new PasswordAuthentication(username, password);
		}
	}

	@Override
	public boolean sendAdminConnectionError(String url) {
		Locale locale = new Locale("en");
		String subject = messageUtils.getSimpleMessage("email.error.subject", locale);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("lang", messageUtils.getSimpleMessage("email.lang", locale));
		model.put("title", messageUtils.getSimpleMessage("email.error.connection.content.title", locale));
		model.put("text", messageUtils.getCustomMessage("email.error.connection.content.text", new String[] { url }, locale));
		model.put("follow1", messageUtils.getSimpleMessage("email.follow.part1", locale));
		model.put("follow2", messageUtils.getSimpleMessage("email.follow.part2", locale));
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "simple.vm", "UTF-8", model);

		if (emailCounterService.isEmailCounterReached(getNonPriorEmailsMaxSentPerDay())) {
			return false;
		}

		try {
			send(ADMIN_EMAIL, ADMIN_EMAIL, subject, message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean sendAdminRetrieverError(String applicationName) {
		Locale locale = new Locale("en");
		String subject = messageUtils.getSimpleMessage("email.error.subject", locale);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("lang", messageUtils.getSimpleMessage("email.lang", locale));
		model.put("title", messageUtils.getSimpleMessage("email.error.retriever.content.title", locale));
		model.put("text", messageUtils.getCustomMessage("email.error.retriever.content.text", new String[] { applicationName }, locale));
		model.put("follow1", messageUtils.getSimpleMessage("email.follow.part1", locale));
		model.put("follow2", messageUtils.getSimpleMessage("email.follow.part2", locale));
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "simple.vm", "UTF-8", model);

		try {
			send(ADMIN_EMAIL, ADMIN_EMAIL, subject, message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean sendAdminRequestedApplication(String name, String url, Locale locale) {
		String subject = messageUtils.getSimpleMessage("email.application.request.subject", locale);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("lang", messageUtils.getSimpleMessage("email.lang", locale));
		model.put("title", messageUtils.getSimpleMessage("email.application.request.content.title", locale));
		model.put("text", messageUtils.getCustomMessage("email.application.request.content.text", new String[] { name, url }, locale));
		model.put("follow1", messageUtils.getSimpleMessage("email.follow.part1", locale));
		model.put("follow2", messageUtils.getSimpleMessage("email.follow.part2", locale));
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "simple.vm", "UTF-8", model);

		try {
			send(ADMIN_EMAIL, ADMIN_EMAIL, subject, message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean sendSingleUpdate(String email, NewVersion newVersion, List<UpdateUrl> otherUpdateUrls, Locale locale) {
		String subject = messageUtils.getCustomMessage("email.application.update.single.subject", new String[] { newVersion.getApplicationName() }, locale);
		String fromEmail = messageUtils.getSimpleMessage("email.noreply", locale);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("lang", messageUtils.getSimpleMessage("email.lang", locale));
		model.put("title", messageUtils.getSimpleMessage("email.application.update.single.title", locale));
		model.put("text1", messageUtils.getCustomMessage("email.application.update.single.text1", new String[] { newVersion.getApplicationName(), newVersion.getVersionNumber() }, locale));
		model.put("button", messageUtils.getSimpleMessage("email.application.update.single.button", locale));
		model.put("text2", buildMessageOtherUpdates(otherUpdateUrls, newVersion.getVersionNumber(), locale));
		model.put("link", newVersion.getUpdateUrl().getUrl());
		model.put("follow1", messageUtils.getSimpleMessage("email.follow.part1", locale));
		model.put("follow2", messageUtils.getSimpleMessage("email.follow.part2", locale));
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "single-update.vm", "UTF-8", model);

		if (emailCounterService.isEmailCounterReached(getNonPriorEmailsMaxSentPerDay())) {
			return false;
		}

		try {
			send(fromEmail, email, subject, message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private String buildMessageOtherUpdates(List<UpdateUrl> otherUpdateUrls, String version, Locale locale) {
		if (otherUpdateUrls.isEmpty()) {
			return StringUtils.EMPTY;
		}
		StringBuilder message = new StringBuilder(messageUtils.getCustomMessage("email.application.update.single.text2.part1", new String[] { version }, locale));
		for (UpdateUrl updateUrl : otherUpdateUrls) {
			message.append(messageUtils.getCustomMessage("email.application.update.single.text2.list.begin", new String[] { updateUrl.getUrl() }, locale));
			message.append(messageUtils.getSimpleMessage("email.application.update.single.text2.list." + updateUrl.getKey(), locale));
			message.append(messageUtils.getSimpleMessage("email.application.update.single.text2.list.end", locale));
		}
		message.append(messageUtils.getSimpleMessage("email.application.update.single.text2.part2", locale));
		return message.toString();
	}

	@Override
	public boolean sendWeeklyUpdates(String email, List<NewVersion> newVersions, Locale locale) {
		String subject;
		if (newVersions.size() == 1) {
			subject = messageUtils.getSimpleMessage("email.application.update.weekly.subject.single", locale);
		} else {
			subject = messageUtils.getCustomMessage("email.application.update.weekly.subject.multi", new Integer[] { newVersions.size() }, locale);
		}
		String fromEmail = messageUtils.getSimpleMessage("email.noreply", locale);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("lang", messageUtils.getSimpleMessage("email.lang", locale));
		model.put("title", messageUtils.getSimpleMessage("email.application.update.weekly.title", locale));
		model.put("text1", messageUtils.getSimpleMessage("email.application.update.weekly.text1.part1", locale));
		model.put("text1", buildMessageWeeklyUpdates(newVersions, locale));
		model.put("follow1", messageUtils.getSimpleMessage("email.follow.part1", locale));
		model.put("follow2", messageUtils.getSimpleMessage("email.follow.part2", locale));
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "weekly-update.vm", "UTF-8", model);

		if (emailCounterService.isEmailCounterReached(getNonPriorEmailsMaxSentPerDay())) {
			return false;
		}

		try {
			send(fromEmail, email, subject, message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private String buildMessageWeeklyUpdates(List<NewVersion> newVersions, Locale locale) {
		StringBuilder message = new StringBuilder(messageUtils.getSimpleMessage("email.application.update.weekly.text1.part1", locale));
		for (NewVersion newVersion : newVersions) {
			message.append(messageUtils.getCustomMessage("email.application.update.weekly.text1.list.begin", new String[] { newVersion.getUpdateUrl().getUrl() }, locale));
			message.append(messageUtils.getCustomMessage("email.application.update.weekly.text1.list.version", new String[] { newVersion.getApplicationName(), newVersion.getVersionNumber() }, locale));
			message.append(messageUtils.getSimpleMessage("email.application.update.weekly.text1.list.end", locale));
		}
		message.append(messageUtils.getSimpleMessage("email.application.update.weekly.text1.part2", locale));
		return message.toString();
	}

}
