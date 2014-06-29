package com.updapy.service;

import java.util.List;

import com.updapy.model.EmailNewsletter;
import com.updapy.model.Newsletter;
import com.updapy.model.User;

public interface NewsletterService {

	List<Newsletter> getNewsletters();

	EmailNewsletter addEmailNewsletter(User user, Newsletter newsletter);

	Newsletter markAsNotified(Newsletter newsletter);

	int sendEmailNewsletters();

}
