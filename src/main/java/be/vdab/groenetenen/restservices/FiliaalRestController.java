package be.vdab.groenetenen.restservices;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.vdab.groenetenen.entities.Filiaal;
import be.vdab.groenetenen.exceptions.FiliaalHeeftNogWerknemersException;
import be.vdab.groenetenen.exceptions.FiliaalNietGevondenException;
import be.vdab.groenetenen.exceptions.FiliaalReedsInDatabaseException;
import be.vdab.groenetenen.services.FiliaalService;

@RestController
@RequestMapping("filialen") //onderscheid maken met @RequestMapping in web-laag!
@ExposesResourceFor(Filiaal.class) // HATEOAS
class FiliaalRestController {
	private final FiliaalService filiaalService;
	private final EntityLinks entityLinks; //HATEOAS

	FiliaalRestController(FiliaalService filiaalService, EntityLinks entityLinks) {
		this.filiaalService = filiaalService;
		this.entityLinks = entityLinks; //HATEOAS
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	HttpHeaders create(@RequestBody @Valid Filiaal filiaal) {
		filiaalService.create(filiaal);
		HttpHeaders headers = new HttpHeaders();
		Link link = entityLinks.linkToSingleResource(Filiaal.class,filiaal.getId()); //<link rel='self' href='/filialen/1' />
		headers.setLocation(URI.create(link.getHref())); //<link rel='self' href='http://localhost:8080/filialen/1' />
		return headers;
	}
	
	@GetMapping("{filiaal}")
	FiliaalResource read(@PathVariable Optional<Filiaal> filiaal) {
		if (!filiaal.isPresent()) {
			throw new FiliaalNietGevondenException();
		}
		return new FiliaalResource(filiaal.get(),entityLinks); // bij HATEOAS maak je dus een FiliaalResource klasse
	}
	
	@PutMapping("{id}")
	void update(@RequestBody @Valid Filiaal filiaal) {
		filiaalService.update(filiaal);
	}
	
	@DeleteMapping("{filiaal}")
	void delete(@PathVariable Optional<Filiaal> filiaal) {
		if (!filiaal.isPresent()) {
			throw new FiliaalNietGevondenException();
		}
		filiaalService.delete(filiaal.get().getId());
	}
	
	@GetMapping
	FilialenResource findAll() {
		return new FilialenResource(filiaalService.findAll(),entityLinks); //HATEOAS je maakt dus ook een FilialenResource klasse
	}
	
	@ExceptionHandler(FiliaalNietGevondenException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	void filiaalNietGevonden() {
		}
	
	@ExceptionHandler(FiliaalHeeftNogWerknemersException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	String filiaalHeeftNogWerknemers() {
		return "filiaal heeft nog werknemers";
	}
	
	@ExceptionHandler(FiliaalReedsInDatabaseException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	String filiaalReedsInDatabase() {
		return "filiaal is reeds opgenomen in de database";
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String filiaalMetVerkeerdeProperties(MethodArgumentNotValidException ex) {
		StringBuilder fouten = new StringBuilder();
		ex.getBindingResult().getFieldErrors().forEach(error ->
				fouten.append(error.getField())
					  .append(" : ")
					  .append(error.getDefaultMessage())
					  .append('\n'));
		fouten.deleteCharAt(fouten.length()-1);
		return fouten.toString();
	}
}
