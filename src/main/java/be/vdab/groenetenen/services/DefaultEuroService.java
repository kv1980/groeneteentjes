package be.vdab.groenetenen.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import be.vdab.groenetenen.restclients.KoersClient;

@Service
class DefaultEuroService implements EuroService {
	private final KoersClient koersClient;
	
	DefaultEuroService(KoersClient koersClient) {
		this.koersClient = koersClient;
	}

	@Override
	public BigDecimal naarDollar(BigDecimal euro) {
		return euro.multiply(koersClient.getDollarKoers()).setScale(2,RoundingMode.HALF_UP);
	}

}
