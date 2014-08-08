package com.updapy.service;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext-test.xml" })
public class SendEmailTest {

	@Autowired
	EmailSenderService emailSenderService;

	@Test
	public void testSendPersonalEmail() {
		String email = "";
		String subject = "How to contact us?";
		String title = "A question? An idea?";
		String message = "You can contact us via this email: xxx<br>Thanks for using the Updapy service!";
		Locale locale = Locale.ENGLISH;
		emailSenderService.sendPersonalEmail(email, subject, title, message, locale);
	}

}
