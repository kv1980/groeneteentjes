package be.vdab.groenetenen.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import be.vdab.groenetenen.entities.Filiaal;

public interface FiliaalRepository extends JpaRepository<Filiaal, Long> {
	List<Filiaal> findByAdresPostcodeBetweenOrderByAdresPostcode(int van, int tot);
	Filiaal findMetHoogsteWaardeGebouw();
	BigDecimal findGemiddeldeWaardeGebouwInGemeente(@Param("gemeente") String gemeente);
}