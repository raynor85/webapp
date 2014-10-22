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
		String subject = "Error when connecting to Updapy via Google +";
		String title = "Thanks for reporting your problem";
		String message = "We just fix it! You can <a href='http://www.updapy.com/sign'>sign in to Updapy</a> via Google + again.<br>You will certainly need to re-authorize Updapy to access your basic information (name, email).<br>If you're still having problems to sign in, don't hesitate to contact us.<br><br>The Updapy team.";
		Locale locale = Locale.ENGLISH;
		emailSenderService.sendPersonalEmail(email, subject, title, message, locale);
	}

}
