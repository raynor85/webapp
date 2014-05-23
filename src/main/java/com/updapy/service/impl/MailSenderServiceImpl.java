package com.updapy.service.impl;

import java.util.HashMap;
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

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.updapy.service.MailSenderService;
import com.updapy.util.MessageUtils;

@Service
public class MailSenderServiceImpl implements MailSenderService {

	@Autowired
	MessageUtils messageUtils;

	@Autowired
	VelocityEngine velocityEngine;

	private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
	private static final String SMTP_AUTH_USER = System.getenv("SENDGRID_USERNAME");
	private static final String SMTP_AUTH_PWD = System.getenv("SENDGRID_PASSWORD");

	public boolean sendActivationLink(String email, String key) {
		String link = messageUtils.getSimpleMessage("application.root.url") + "/user/activate?email=" + email + "&key=" + key;
		String fromEmail = messageUtils.getSimpleMessage("email.noreply");
		String subject = messageUtils.getSimpleMessage("email.activate.subject");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("lang", messageUtils.getSimpleMessage("email.lang"));
		model.put("title", messageUtils.getSimpleMessage("email.activate.content.title"));
		model.put("text1", messageUtils.getSimpleMessage("email.activate.content.text1"));
		model.put("button", messageUtils.getSimpleMessage("email.activate.content.button"));
		model.put("text2", messageUtils.getSimpleMessage("email.activate.content.text2"));
		model.put("link", link);
		model.put("text3", "");
		model.put("follow1", messageUtils.getSimpleMessage("email.follow.part1"));
		model.put("follow2", messageUtils.getSimpleMessage("email.follow.part2"));
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "reset-activate-account.vm", "UTF-8", model);
		try {
			send(fromEmail, email, subject, message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean sendResetPasswordLink(String email, String key) {
		String link = messageUtils.getSimpleMessage("application.root.url") + "/user/resetpassword?email=" + email + "&key=" + key;
		String fromEmail = messageUtils.getSimpleMessage("email.noreply");
		String subject = messageUtils.getSimpleMessage("email.reset.subject");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("lang", messageUtils.getSimpleMessage("email.lang"));
		model.put("title", messageUtils.getSimpleMessage("email.reset.content.title"));
		model.put("text1", messageUtils.getSimpleMessage("email.reset.content.text1"));
		model.put("button", messageUtils.getSimpleMessage("email.reset.content.button"));
		model.put("text2", messageUtils.getSimpleMessage("email.reset.content.text2"));
		model.put("link", link);
		model.put("text3", messageUtils.getSimpleMessage("email.reset.content.text3"));
		model.put("follow1", messageUtils.getSimpleMessage("email.follow.part1"));
		model.put("follow2", messageUtils.getSimpleMessage("email.follow.part2"));
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "reset-activate-account.vm", "UTF-8", model);
		try {
			send(fromEmail, email, subject, message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private void send(String fromEmail, String toEmail, String subject, String htmlContent) throws Exception {
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
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = SMTP_AUTH_USER;
			String password = SMTP_AUTH_PWD;
			return new PasswordAuthentication(username, password);
		}
	}
}
