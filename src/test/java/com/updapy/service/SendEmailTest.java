package com.updapy.service;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext-test.xml"})
public class SendEmailTest {

	@Autowired
	EmailSenderService emailSenderService;

	@Test
	public void testSendPersonalEmail() {
		String email = "email@test.com";
		String subject = "Email subject";
		String title = "This is my title";
		String message = "This is my message.<br>How are you?";
		Locale locale = Locale.ENGLISH;
		emailSenderService.sendPersonalEmail(email, subject, title, message, locale);
	}

}
