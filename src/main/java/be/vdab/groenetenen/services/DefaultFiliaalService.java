package be.vdab.groenetenen.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import be.vdab.groenetenen.entities.Filiaal;
import be.vdab.groenetenen.exceptions.FiliaalHeeftNogWerknemersException;
import be.vdab.groenetenen.exceptions.FiliaalReedsInDatabaseException;
import be.vdab.groenetenen.repositories.FiliaalRepository;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
class DefaultFiliaalService implements FiliaalService {
	private final FiliaalRepository repository;

	DefaultFiliaalService(FiliaalRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void create(Filiaal filiaal) {
		if (repository.findAll().contains(filiaal)) {
			throw new FiliaalReedsInDatabaseException();
		}
		repository.save(filiaal);
	}
	
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void update(Filiaal filiaal) {
		repository.save(filiaal);
	}
	
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void delete(long id) {
		Optional<Filiaal> optionalFiliaal = repository.findById(id);
		if (optionalFiliaal.isPresent()) {
			if (!optionalFiliaal.get().getWerknemers().isEmpty()) {
				throw new FiliaalHeeftNogWerknemersException();
			}
			repository.deleteById(id);
		}
	}
	
	@Override
	public List<Filiaal> findAll(){
		return repository.findAll();
	}
	
	@Override
	public List<Filiaal> findByPostcode(int van, int tot) {
		return repository.findByAdresPostcodeBetweenOrderByAdresPostcode(van, tot);
	}

	@Override
	public Filiaal findMetHoogsteWaardeGebouw() {
		return repository.findMetHoogsteWaardeGebouw();
	}

	@Override
	public BigDecimal findGemiddeldeWaardeGebouwInGemeente(String gemeente) {
		return repository.findGemiddeldeWaardeGebouwInGemeente(gemeente);
	}

	@Override
	@PreAuthorize("hasAuthority('manager')")
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void afschrijven(long id) {
		repository.findById(id).ifPresent(filiaal -> filiaal.afschrijven());
	}
}
