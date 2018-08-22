package be.vdab.groenetenen.restclients;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import be.vdab.groenetenen.entities.Filiaal;
import be.vdab.groenetenen.exceptions.FiliaalNietGevondenException;
import be.vdab.groenetenen.restservices.FiliaalResource;

@Component
class DefaultFiliaalClient implements FiliaalClient{
	private URI filialenURL;
	private final RestTemplate restTemplate;
	
	DefaultFiliaalClient(@Value("${filialenURL}") URI filialenURL, RestTemplateBuilder restTemplateBuilder){
		this.filialenURL = filialenURL;
		this.restTemplate = restTemplateBuilder.build();
	}
	
	private URI filialenURLMetId(long id) {
		return URI.create(filialenURL.toString()+"/"+id);	
	}
	
	@Override
	public void postFiliaal(Filiaal filiaal) {
		try {
			URI uri = restTemplate.postForLocation(filialenURL, filiaal);
		} catch (HttpStatusCodeException ex) {
			if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
				 String foutMeldingen = ex.getResponseBodyAsString();
				 // hier komt code als de request verkeerde data bevatte
			}
		}
	}

	@Override
	public Filiaal getFiliaal(long id) {
		try {
			return restTemplate.getForObject(filialenURLMetId(id),FiliaalResource.class).getFiliaal();
		}
		catch (HttpStatusCodeException ex) {
			throw new FiliaalNietGevondenException();
		}
	}

	@Override
	public void updateFiliaal(long id, Filiaal filiaal) {
		try {   
			restTemplate.put(filialenURLMetId(id), filiaal);
		} catch (HttpStatusCodeException ex) {
			switch (ex.getStatusCode()) {
				case NOT_FOUND:       
					// hier komt code als het filiaal niet bestaat
				case BAD_REQUEST:
					String foutMeldingen = ex.getResponseBodyAsString();
					// hier komt code als de request verkeerde data bevatte   } } 
			}
		}
		
	}

	@Override
	public void deleteFiliaal(long id) {
		try {
			restTemplate.delete(filialenURLMetId(id));    
		} catch (HttpStatusCodeException ex) {
			switch (ex.getStatusCode()) {
				case NOT_FOUND:
					// hier komt code als het filiaal niet gevonden is
				case CONFLICT:
					// hier komt code als het filiaal niet kon verwijderd worden 
					// omdat het bijvoorbeeld nog werknemers heeft 
			}   
		} 		
	}
}
