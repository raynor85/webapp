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
	public void sendPersonalEmailAnswer1En() {
		String email = "";
		String subject = "Error when connecting to Updapy via Google +";
		String title = "Thanks for reporting your problem";
		String message = "We just fix it! You can <a href='http://www.updapy.com/sign'>sign in to Updapy</a> via Google + again.<br>You will certainly need to re-authorize Updapy to access your basic information (name, email).<br>If you're still having problems to sign in, don't hesitate to contact us.<br><br>The Updapy team.";
		Locale locale = Locale.ENGLISH;
		emailSenderService.sendPersonalEmail(email, subject, title, message, locale);
	}

	@Test
	public void sendPersonalEmailApplicationRefusedFr() {
		String email = "";
		String subject = "A propos de vos demandes d'ajout";
		String title = "Merci pour vos récentes demandes d'ajout d'application";
		String message = "Malheureusement, nous ne pouvons satisfaire votre demande...<br>Lumia Player et Lumia Browser ne répondent pas à nos exigences pour pouvoir être ajoutés sur Updapy.<br>Nous comptons sur votre compréhension.<br><br>L'équipe Updapy.";
		Locale locale = Locale.FRENCH;
		emailSenderService.sendPersonalEmail(email, subject, title, message, locale);
	}
	
	@Test
	public void sendPersonalEmailAnswer2Fr() {
		String email = "";
		String subject = "Réponse à votre message";
		String title = "Erreur 403";
		String message = "Merci pour votre retour. Malheureusement, nous ne reproduisons pas votre problème.<br>Une erreur 403 indique un accès refusé.<br>Essayez de vous déconnecter puis de vous reconnecter avant de suivre une nouvelle application.<br>Il est possible qu'un problème de cookie en soit la cause, en particulier si vous utilisez la fonctionnalité \"Se souvenir de moi\".<br><br>L'équipe Updapy.";
		Locale locale = Locale.FRENCH;
		emailSenderService.sendPersonalEmail(email, subject, title, message, locale);
	}
	
}
