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

import com.updapy.form.model.enumeration.SubjectMessage;
import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.Newsletter;
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

	private static final String ADMIN_EMAIL = System.getenv("ADMIN_EMAIL");
	private static final String CONTACT_EMAIL = System.getenv("CONTACT_EMAIL");
	private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
	private static final String SMTP_AUTH_USER = System.getenv("SENDGRID_USERNAME");
	private static final String SMTP_AUTH_PWD = System.getenv("SENDGRID_PASSWORD");

	@Override
	public int getNonPriorEmailsMaxSentPerDay() {
		return Integer.parseInt(messageUtils.getSimpleMessage("email.send.max"));
	}

	@Override
	public boolean sendActivationLink(String email, String key, Locale locale) {
		String link = messageUtils.getSimpleMessage("application.root.url") + "/user/activate?email=" + email + "&key=" + key;
		String fromEmail = messageUtils.getSimpleMessage("email.noreply", locale);
		String subject = messageUtils.getSimpleMessage("email.activate.subject", locale);
		Map<String, Object> model = new HashMap<String, Object>();
		setLang(locale, model);
		model.put("title", messageUtils.getSimpleMessage("email.activate.content.title", locale));
		model.put("text1", messageUtils.getSimpleMessage("email.activate.content.text1", locale));
		model.put("button", messageUtils.getSimpleMessage("email.activate.content.button", locale));
		model.put("text2", messageUtils.getSimpleMessage("email.activate.content.text2", locale));
		model.put("link", link);
		model.put("text3", "");
		setFollowMessages(locale, model);
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
		String link = messageUtils.getSimpleMessage("application.root.url") + "/user/reset/password?email=" + email + "&key=" + key;
		String fromEmail = messageUtils.getSimpleMessage("email.noreply", locale);
		String subject = messageUtils.getSimpleMessage("email.reset.subject", locale);
		Map<String, Object> model = new HashMap<String, Object>();
		setLang(locale, model);
		model.put("title", messageUtils.getSimpleMessage("email.reset.content.title", locale));
		model.put("text1", messageUtils.getSimpleMessage("email.reset.content.text1", locale));
		model.put("button", messageUtils.getSimpleMessage("email.reset.content.button", locale));
		model.put("text2", messageUtils.getSimpleMessage("email.reset.content.text2", locale));
		model.put("link", link);
		model.put("text3", messageUtils.getSimpleMessage("email.reset.content.text3", locale));
		setFollowMessages(locale, model);
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "reset-activate-account.vm", "UTF-8", model);

		try {
			send(fromEmail, email, subject, message);
			return true;
		} catch (Exception e) {
			return false;
		}
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
		setLang(locale, model);
		model.put("title", messageUtils.getSimpleMessage("email.error.connection.content.title", locale));
		model.put("text", messageUtils.getCustomMessage("email.error.connection.content.text", new String[] { url }, locale));
		model.put("signature", StringUtils.EMPTY);
		setFollowMessages(locale, model);
		model.put("unsubscribetext", StringUtils.EMPTY);
		model.put("donate", StringUtils.EMPTY);
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
		setLang(locale, model);
		model.put("title", messageUtils.getSimpleMessage("email.error.retriever.content.title", locale));
		model.put("text", messageUtils.getCustomMessage("email.error.retriever.content.text", new String[] { applicationName }, locale));
		model.put("signature", StringUtils.EMPTY);
		setFollowMessages(locale, model);
		model.put("unsubscribetext", StringUtils.EMPTY);
		model.put("donate", StringUtils.EMPTY);
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "simple.vm", "UTF-8", model);

		try {
			send(ADMIN_EMAIL, ADMIN_EMAIL, subject, message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean sendAdminRequestedApplication(String email, String name, String url, Locale locale) {
		String subject = messageUtils.getSimpleMessage("email.application.request.subject", locale);
		Map<String, Object> model = new HashMap<String, Object>();
		setLang(locale, model);
		model.put("title", messageUtils.getSimpleMessage("email.application.request.content.title", locale));
		model.put("text", messageUtils.getCustomMessage("email.application.request.content.text", new String[] { email, name, url }, locale));
		model.put("signature", StringUtils.EMPTY);
		setFollowMessages(locale, model);
		model.put("unsubscribetext", StringUtils.EMPTY);
		model.put("donate", StringUtils.EMPTY);
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "simple.vm", "UTF-8", model);

		try {
			send(ADMIN_EMAIL, ADMIN_EMAIL, subject, message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean sendAdminMessage(String email, SubjectMessage subject, String text, boolean anonymous) {
		Locale locale = new Locale("en");
		String subjectAsString;
		if (anonymous) {
			subjectAsString = messageUtils.getSimpleMessage("email.contact.anonymous.subject", locale);
		} else {
			subjectAsString = messageUtils.getSimpleMessage("email.contact.user.subject", locale);
		}
		subjectAsString += " - " + messageUtils.getSimpleMessage("contact.field.subject." + subject.name(), locale);
		Map<String, Object> model = new HashMap<String, Object>();
		setLang(locale, model);
		model.put("title", messageUtils.getCustomMessage("email.contact.content.title", new String[] { email }, locale));
		model.put("text", text);
		model.put("signature", StringUtils.EMPTY);
		setFollowMessages(locale, model);
		model.put("unsubscribetext", StringUtils.EMPTY);
		model.put("donate", StringUtils.EMPTY);
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "simple.vm", "UTF-8", model);

		try {
			send(ADMIN_EMAIL, ADMIN_EMAIL, subjectAsString, message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean sendSingleUpdate(String email, String key, ApplicationVersion newVersion, Locale locale) {
		String subject = messageUtils.getCustomMessage("email.application.update.single.subject", new String[] { newVersion.getApplication().getName() }, locale);
		String fromEmail = messageUtils.getSimpleMessage("email.noreply", locale);
		Map<String, Object> model = new HashMap<String, Object>();
		setLang(locale, model);
		model.put("title", messageUtils.getSimpleMessage("email.application.update.single.title", locale));
		model.put("text1", messageUtils.getCustomMessage("email.application.update.single.text1", new String[] { newVersion.getApplication().getName(), newVersion.getVersionNumber() }, locale));
		model.put("button", messageUtils.getSimpleMessage("email.application.update.single.button", locale));
		model.put("link", buildDownloadUrl(newVersion, key));
		setFollowMessages(locale, model);
		setDonateMessage(locale, model);
		model.put("rsstiptext", messageUtils.getSimpleMessage("email.application.update.tip", locale));
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

	@Override
	public boolean sendWeeklyUpdates(String email, String key, List<ApplicationVersion> newVersions, Locale locale) {
		String subject;
		if (newVersions.size() == 1) {
			subject = messageUtils.getSimpleMessage("email.application.update.weekly.subject.single", locale);
		} else {
			subject = messageUtils.getCustomMessage("email.application.update.weekly.subject.multi", new Integer[] { newVersions.size() }, locale);
		}
		String fromEmail = messageUtils.getSimpleMessage("email.noreply", locale);
		Map<String, Object> model = new HashMap<String, Object>();
		setLang(locale, model);
		model.put("title", messageUtils.getSimpleMessage("email.application.update.weekly.title", locale));
		model.put("text1", messageUtils.getSimpleMessage("email.application.update.weekly.text1.part1", locale));
		model.put("text1", buildMessageWeeklyUpdates(newVersions, key, locale));
		setFollowMessages(locale, model);
		setDonateMessage(locale, model);
		model.put("rsstiptext", messageUtils.getSimpleMessage("email.application.update.tip", locale));
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

	private String buildMessageWeeklyUpdates(List<ApplicationVersion> newVersions, String key, Locale locale) {
		StringBuilder message = new StringBuilder(messageUtils.getSimpleMessage("email.application.update.weekly.text1.part1", locale));
		for (ApplicationVersion newVersion : newVersions) {
			message.append(messageUtils.getCustomMessage("email.application.update.weekly.text1.list.begin", new String[] { buildDownloadUrl(newVersion, key) }, locale));
			message.append(messageUtils.getCustomMessage("email.application.update.weekly.text1.list.version", new String[] { newVersion.getApplication().getName(), newVersion.getVersionNumber() }, locale));
			message.append(messageUtils.getSimpleMessage("email.application.update.weekly.text1.list.end", locale));
		}
		message.append(messageUtils.getSimpleMessage("email.application.update.weekly.text1.part2", locale));
		return message.toString();
	}

	private String buildDownloadUrl(ApplicationVersion newVersion, String key) {
		return messageUtils.getSimpleMessage("application.root.url") + "/applications/" + newVersion.getApplication().getApiName() + "/download?key=" + key;
	}

	@Override
	public boolean sendAddedApplication(String email, List<ApplicationReference> applications, Locale locale) {
		String subject = StringUtils.EMPTY;
		String fromEmail = messageUtils.getSimpleMessage("email.noreply", locale);
		Map<String, Object> model = new HashMap<String, Object>();
		setLang(locale, model);
		model.put("title", messageUtils.getSimpleMessage("email.application.added.content.title", locale));
		if (applications.size() == 1) {
			// only one application added
			ApplicationReference application = applications.get(0);
			subject = messageUtils.getCustomMessage("email.application.added.subject.single", new String[] { application.getName() }, locale);
			model.put("text", messageUtils.getCustomMessage("email.application.added.content.text.single", new String[] { application.getName(), application.getApiName() }, locale));
		} else {
			// more than one
			subject = messageUtils.getCustomMessage("email.application.added.subject.multi", new Integer[] { applications.size() }, locale);
			model.put("text", buildMessageAddedApplications(applications, locale));
		}
		setSignature(locale, model);
		setFollowMessages(locale, model);
		setDonateMessage(locale, model);
		model.put("unsubscribetext", messageUtils.getSimpleMessage("email.application.added.unsubscribe", locale));
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "simple.vm", "UTF-8", model);

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

	private String buildMessageAddedApplications(List<ApplicationReference> applications, Locale locale) {
		StringBuilder message = new StringBuilder(messageUtils.getSimpleMessage("email.application.added.text.multi.part1", locale));
		for (ApplicationReference application : applications) {
			message.append(messageUtils.getCustomMessage("email.application.added.text.multi.item", new String[] { application.getName(), application.getApiName() }, locale));
		}
		message.append(messageUtils.getSimpleMessage("email.application.added.text.multi.part2", locale));
		return message.toString();
	}

	@Override
	public boolean sendDeletedApplication(String email, ApplicationReference application, Locale locale) {
		String name = application.getName();
		String fromEmail = messageUtils.getSimpleMessage("email.noreply", locale);
		String subject = messageUtils.getCustomMessage("email.application.deleted.subject", new String[] { name }, locale);
		Map<String, Object> model = new HashMap<String, Object>();
		setLang(locale, model);
		model.put("title", messageUtils.getSimpleMessage("email.application.deleted.content.title", locale));
		model.put("text", messageUtils.getCustomMessage("email.application.deleted.content.text", new String[] { name }, locale));
		setSignature(locale, model);
		setFollowMessages(locale, model);
		setDonateMessage(locale, model);
		model.put("unsubscribetext", StringUtils.EMPTY);
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "simple.vm", "UTF-8", model);

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

	@Override
	public boolean sendNewsletter(String email, Newsletter newsletter, Locale locale) {
		String fromEmail = messageUtils.getSimpleMessage("email.noreply", locale);
		String subject = newsletter.getSubject(locale);
		Map<String, Object> model = new HashMap<String, Object>();
		setLang(locale, model);
		model.put("title", newsletter.getTitle(locale));
		model.put("text", newsletter.getText(locale));
		setSignature(locale, model);
		setFollowMessages(locale, model);
		setDonateMessage(locale, model);
		model.put("unsubscribetext", messageUtils.getSimpleMessage("email.newsletter.unsubscribe", locale));
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "simple.vm", "UTF-8", model);

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

	@Override
	public boolean sendPersonalEmail(String email, String subject, String title, String message, Locale locale) {
		Map<String, Object> model = new HashMap<String, Object>();
		setLang(locale, model);
		model.put("title", title);
		model.put("text", message);
		setSignature(locale, model);
		setFollowMessages(locale, model);
		setDonateMessage(locale, model);
		model.put("unsubscribetext", StringUtils.EMPTY);
		String fullMessage = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "simple.vm", "UTF-8", model);

		try {
			send(CONTACT_EMAIL, email, subject, fullMessage);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private void setLang(Locale locale, Map<String, Object> model) {
		model.put("lang", messageUtils.getSimpleMessage("email.lang", locale));
	}

	private void setSignature(Locale locale, Map<String, Object> model) {
		model.put("signature", messageUtils.getSimpleMessage("email.signature", locale));
	}

	private void setFollowMessages(Locale locale, Map<String, Object> model) {
		model.put("follow1", messageUtils.getSimpleMessage("email.follow.part1", locale));
		model.put("follow2", messageUtils.getSimpleMessage("email.follow.part2", locale));
	}

	private void setDonateMessage(Locale locale, Map<String, Object> model) {
		model.put("donate", messageUtils.getSimpleMessage("email.donate", locale));
	}

	private void send(String fromEmail, String toEmail, String subject, String htmlContent) throws Exception {
		if (messageUtils.getSimpleMessage("environnement").equals("dev")) {
			// skip email sending
			log.info("Sending email to '{}' with subject '{}' and content '{}'", toEmail, subject, htmlContent);
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

}
