package be.vdab.groenetenen.mail;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import be.vdab.groenetenen.entities.Offerte;
import be.vdab.groenetenen.exceptions.KanMailNietZendenException;

@Component
class DefaultMailSender implements MailSender {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMailSender.class);
	private final JavaMailSender sender;
	private final String emailWebmaster;
	
	DefaultMailSender(JavaMailSender sender, @Value("${emailWebmaster}") String emailWebmaster) {
		this.sender = sender;
		this.emailWebmaster = emailWebmaster;
	}

	@Async
	@Override
	public void nieuweOfferte(Offerte offerte,String offertesURL) {
		try {
/*			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(offerte.getEmailAdres());
			message.setSubject("nieuwe offerte");
			message.setText("Uw offerte heeft nummer "+offerte.getId());*/
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setTo(offerte.getEmailAdres());
			helper.setSubject("nieuwe offerte");
			String offerteURL = offertesURL + offerte.getId();
			helper.setText("Uw offerte heeft het nummer <strong>"+offerte.getId()+"</strong>. U vindt de offerte <a href='"+offerteURL+"'>hier</a>.",true);
			sender.send(message);
		} catch (MessagingException|MailException ex) {
			LOGGER.error("Kan mail met nieuwe offerte niet versturen",ex);
			throw new KanMailNietZendenException();
		}
	}
	
	@Override
	public void stuurRegelmatigNaarWebmaster(long aantalOffertes) {
		try {
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setTo(emailWebmaster);
			helper.setSubject("Aantal offertes");
			helper.setText("Aantal offertes: <strong>"+aantalOffertes+"</strong>.", true);
			sender.send(message);
		} catch (MessagingException|MailException ex) {
			LOGGER.error("Kan mail met nieuwe offerte niet versturen",ex);
			throw new KanMailNietZendenException();
		}
	}
}