package be.vdab.groenetenen.restclients;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import be.vdab.groenetenen.entities.Filiaal;
import be.vdab.groenetenen.exceptions.FiliaalNietGevondenException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT) // alle bean openen + poort is specifiek 8080 zoals in URL beschreven

public class DefaultFiliaalClientTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private DefaultFiliaalClient client;
	
	@Test
	public void getFiliaal_geeft_een_bestaand_filiaal_terug() {
		Filiaal filiaal = client.getFiliaal(1); // dit was een resource en geen rechtstreeks filiaal
		assertEquals("Andros",filiaal.getNaam());
	}
	
	@Test (expected = FiliaalNietGevondenException.class)
	public void getFiliaal_geeft_geen_onbestaand_filiaal_terug() {
		Filiaal filiaal = client.getFiliaal(9999999);
	}
}