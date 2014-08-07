package com.updapy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.updapy.model.EmailNewsletter;
import com.updapy.model.Newsletter;
import com.updapy.model.User;
import com.updapy.repository.EmailNewsletterRepository;
import com.updapy.repository.NewsletterRepository;
import com.updapy.service.EmailSenderService;
import com.updapy.service.NewsletterService;
import com.updapy.service.UserService;

@Service
public class NewsletterServiceImpl implements NewsletterService {

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private UserService userService;

	@Autowired
	private NewsletterRepository newsletterRepository;

	@Autowired
	private EmailNewsletterRepository emailNewsletterRepository;

	@Override
	public List<Newsletter> getNewsletters() {
		return newsletterRepository.findByNotifiedFalseAndActiveTrue();
	}

	@Override
	public EmailNewsletter addEmailNewsletter(User user, Newsletter newsletter) {
		EmailNewsletter emailNewsletter = new EmailNewsletter();
		emailNewsletter.setUser(user);
		emailNewsletter.setNewsletter(newsletter);
		emailNewsletter.setSent(false);
		return save(emailNewsletter);
	}

	@Override
	public Newsletter markAsNotified(Newsletter newsletter) {
		newsletter.setNotified(true);
		return newsletterRepository.saveAndFlush(newsletter);
	}

	@Override
	public int sendEmailNewsletters() {
		List<EmailNewsletter> emailNewsletters = emailNewsletterRepository.findBySentFalse(new PageRequest(0, emailSenderService.getNonPriorEmailsMaxSentPerDay()));
		int count = 0;
		for (EmailNewsletter emailNewsletter : emailNewsletters) {
			User user = emailNewsletter.getUser();
			boolean hasBeenSent = emailSenderService.sendNewsletter(user.getEmail(), emailNewsletter.getNewsletter(), user.getLangEmail());
			if (hasBeenSent) {
				emailNewsletter.setSent(true);
				save(emailNewsletter);
				count++;
			}
		}
		return count;
	}

	private EmailNewsletter save(EmailNewsletter emailNewsletter) {
		return emailNewsletterRepository.saveAndFlush(emailNewsletter);
	}

}
