package be.vdab.groenetenen.restclients;

import be.vdab.groenetenen.entities.Filiaal;

public interface FiliaalClient {
	void postFiliaal(Filiaal filiaal);
	Filiaal getFiliaal(long id);
	void updateFiliaal(long id, Filiaal filiaal);
	void deleteFiliaal(long id);
}