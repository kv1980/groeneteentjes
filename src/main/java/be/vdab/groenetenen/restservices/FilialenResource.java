package be.vdab.groenetenen.restservices;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import be.vdab.groenetenen.entities.Filiaal;

@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
class FilialenResource extends ResourceSupport{
	@XmlElement(name="filiaal")
	@JsonProperty("filialen")
	private final List<FiliaalIdNaam> filialenIdNaam = new ArrayList<>();
	
	FilialenResource(){};
	
	FilialenResource(Iterable<Filiaal> filialen, EntityLinks entityLinks){
		for (Filiaal filiaal : filialen) {
			this.filialenIdNaam.add(new FiliaalIdNaam(filiaal));
			//<filiaal id="1" naam="Andros"/> 
			//<filiaal id="2" naam="Delos"/>
			this.add(entityLinks.linkToSingleResource(Filiaal.class,filiaal.getId()).withRel("detail."+filiaal.getId()));
			//<atom:link href="/filialen/1" rel="detail.1"/>
			//<atom:link href="/filialen/2" rel="detail.2"/
		}
		this.add(entityLinks.linkToCollectionResource(Filiaal.class));
		//<atom:link href="/filialen" rel="self"/>
	}
}
