package be.vdab.groenetenen.services;

import java.math.BigDecimal;
import java.util.List;

import be.vdab.groenetenen.entities.Filiaal;

public interface FiliaalService {
	void create(Filiaal filiaal);
	void update(Filiaal filiaal);
	void delete(long id);

	List<Filiaal> findAll();	
	List<Filiaal> findByPostcode(int van, int tot);
	Filiaal findMetHoogsteWaardeGebouw();
	BigDecimal findGemiddeldeWaardeGebouwInGemeente(String gemeente);
	
}