package be.vdab.groenetenen.restservices;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql("/insertFiliaal.sql")
public class FiliaalControllerTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	private MockMvc mvc;
	
	private long idVanTestFiliaal() {
		return super.jdbcTemplate.queryForObject("select id from filialen where naam='test'", Long.class);   
	}
	
	@Test   
	public void filiaalLezenDatNietBestaat() throws Exception {     
		mvc.perform(get("/filialen/-1")
		   .header(HttpHeaders.AUTHORIZATION,"Basic "+Base64Utils.encodeToString("joe:theboss".getBytes()))	
		   .accept(MediaType.APPLICATION_XML)) 
		   .andExpect(status().isNotFound()); 
	}
	
	@Test   
	public void filiaalLezenDatBestaatInXmlFormaat() throws Exception {    
		long id = idVanTestFiliaal();
		mvc.perform(get("/filialen/"+id)
		   .header(HttpHeaders.AUTHORIZATION,"Basic "+Base64Utils.encodeToString("joe:theboss".getBytes()))		
		   .accept(MediaType.APPLICATION_XML)) 
		   .andExpect(status().isOk()) 
		   .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
		   .andExpect(xpath("/filiaalResource/filiaal/id").string(String.valueOf(id)));
	}
	
	@Test   
	public void filiaalDatBestaatLezenInJSONFormaat() throws Exception {
		long id = idVanTestFiliaal();
		mvc.perform(get("/filialen/" + id)
		   .header(HttpHeaders.AUTHORIZATION,"Basic "+Base64Utils.encodeToString("joe:theboss".getBytes()))	
		   .accept(MediaType.APPLICATION_JSON))
		   .andExpect(status().isOk())
		   .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		   .andExpect(jsonPath("$.filiaal.id").value((int) id)); 
	}
}
