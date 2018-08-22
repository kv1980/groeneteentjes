package be.vdab.groenetenen.restclients;

import java.math.BigDecimal;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import be.vdab.groenetenen.exceptions.KanKoersNietLezenException;

@Component
class FixerKoersClient implements KoersClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(FixerKoersClient.class);
	private final URI fixerURL;
	private final RestTemplate restTemplate;
	
	FixerKoersClient(@Value("${fixerKoersURL}") URI fixerURL, RestTemplateBuilder restTemplateBuilder){
		this.fixerURL = fixerURL;
		this.restTemplate = restTemplateBuilder.build();
	}
	
	@Override
	public BigDecimal getDollarKoers() {
		try {
			FixerData fixerData = restTemplate.getForObject(fixerURL,FixerData.class);
			return fixerData.getRates().getUsdValue();
		} catch (RestClientException ex) {
			LOGGER.error("De koers kan niet uit Fixer worden gelezen.",ex);
			throw new KanKoersNietLezenException();
		}
	}
}
