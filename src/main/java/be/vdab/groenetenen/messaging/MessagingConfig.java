package be.vdab.groenetenen.messaging;

import javax.jms.ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class MessagingConfig {
	@Bean
	Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(OfferteEnOffertesURL.class);
		return marshaller;
	}

	@Bean
	MarshallingMessageConverter converter(Jaxb2Marshaller marshaller) {
		return new MarshallingMessageConverter(marshaller, marshaller);
	}

	@Bean
	DefaultJmsListenerContainerFactory factory(ConnectionFactory connectionFactory,
			MarshallingMessageConverter converter) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(converter);
		return factory;
	}
}
