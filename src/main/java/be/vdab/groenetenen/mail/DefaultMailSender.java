package be.vdab.groenetenen.mail;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import be.vdab.groenetenen.entities.Offerte;
import be.vdab.groenetenen.exceptions.KanMailNietZendenException;

@Component
class DefaultMailSender implements MailSender {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMailSender.class);
	private final JavaMailSender sender;
	
	DefaultMailSender(JavaMailSender sender) {
		this.sender = sender;
	}

	@Override
	public void nieuweOfferte(Offerte offerte) {
		System.out.println("---------------------"+offerte.getEmailAdres());
		System.out.println("---------------------"+offerte.getId());
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(offerte.getEmailAdres());
			message.setSubject("nieuwe offerte");
			message.setText("Uw offerte heeft nummer "+offerte.getId());
			sender.send(message);
		} catch (MailException ex) {
			LOGGER.error("Kan mail met nieuwe offerte niet versturen",ex);
			throw new KanMailNietZendenException();
		}
	}
}