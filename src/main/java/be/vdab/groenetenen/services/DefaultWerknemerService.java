package be.vdab.groenetenen.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import be.vdab.groenetenen.entities.Werknemer;
import be.vdab.groenetenen.repositories.WerknemerRepository;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
class DefaultWerknemerService implements WerknemerService {
	private final WerknemerRepository repository;

	DefaultWerknemerService(WerknemerRepository repository) {
		this.repository = repository;
	}

	@Override
	public Page<Werknemer> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
}