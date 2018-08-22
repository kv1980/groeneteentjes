package be.vdab.groenetenen.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import be.vdab.groenetenen.entities.Werknemer;

public interface WerknemerRepository extends JpaRepository<Werknemer, Long> {
/*  @EntityGraph(Werknemer.MET_FILIAAL)
	Werknemer findByRijksregisterNr(long rijksRegisterNr);
*/
	@Override
	@EntityGraph(Werknemer.MET_FILIAAL)
	Page<Werknemer> findAll(Pageable pageable);
}