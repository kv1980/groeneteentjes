package be.vdab.groenetenen.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import be.vdab.groenetenen.entities.Offerte;
import be.vdab.groenetenen.mail.MailSender;
import be.vdab.groenetenen.messaging.OfferteEnOffertesURL;
import be.vdab.groenetenen.repositories.OfferteRepository;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
class DefaultOfferteService implements OfferteService {
	private final OfferteRepository offerteRepository;
	private final MailSender mailSender;
	private final JmsTemplate jmsTemplate;
	private final String nieuweOfferteQueue;

	DefaultOfferteService(OfferteRepository offerteRepository, MailSender mailSender,
		JmsTemplate jmsTemplate, @Value("${nieuweOfferteQueue}") String nieuweOfferteQueue) {
		this.offerteRepository = offerteRepository;
		this.mailSender = mailSender;
		this.jmsTemplate = jmsTemplate;
		this.nieuweOfferteQueue = nieuweOfferteQueue;
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void create(Offerte offerte, String offertesURL) {
		offerteRepository.save(offerte);
		//mailSender.nieuweOfferte(offerte,offertesURL);
		OfferteEnOffertesURL offerteEnOffertesURL = new OfferteEnOffertesURL(offerte,offertesURL);
		jmsTemplate.convertAndSend(nieuweOfferteQueue,offerteEnOffertesURL);
	}

	@Override
	public Optional<Offerte> read(long id) {
		return offerteRepository.findById(id);
	}

	@Override
	//@Scheduled(fixedRate=60000)
	public void stuurRegelmatigNaarWebMaster() {
		mailSender.stuurRegelmatigNaarWebmaster(offerteRepository.count());
	}
}