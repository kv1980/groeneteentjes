package be.vdab.groenetenen.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import be.vdab.groenetenen.entities.Filiaal;
import be.vdab.groenetenen.valueobjects.Adres;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FiliaalRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
	private static final String FILIALEN = "filialen";
	@Autowired
	private FiliaalRepository repository;
	
	@Test
	public void create() {
		Adres adres = new Adres("straat","huisNr",1000,"gemeente");
		Filiaal filiaal = new Filiaal();
		filiaal.setNaam("naam");
		filiaal.setAdres(adres);
		filiaal.setWaardeGebouw(BigDecimal.ZERO);
		filiaal.setInGebruikName(LocalDate.now());
		int aantalFilialen = super.countRowsInTable(FILIALEN);
		repository.save(filiaal);
		assertEquals(aantalFilialen+1,super.countRowsInTable(FILIALEN));
		assertNotEquals(0,filiaal.getId());
		assertEquals(1,super.countRowsInTableWhere(FILIALEN, "id="+filiaal.getId()));	
	}
}
