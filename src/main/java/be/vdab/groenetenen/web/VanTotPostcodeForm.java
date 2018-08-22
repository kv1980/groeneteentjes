package be.vdab.groenetenen.web;

import javax.validation.constraints.NotNull;

import be.vdab.groenetenen.constraints.Postcode;
import be.vdab.groenetenen.constraints.VanTotPostcodeFormVanKleinerDanOfGelijkAanTot;

@VanTotPostcodeFormVanKleinerDanOfGelijkAanTot
public class VanTotPostcodeForm {
	@NotNull
	@Postcode
	private Integer van;
	@NotNull
	@Postcode
	private Integer tot;

	public void setVan(Integer van) {
		this.van = van;
	}

	public void setTot(Integer tot) {
		this.tot = tot;
	}

	public Integer getVan() {
		return van;
	}

	public Integer getTot() {
		return tot;
	}
}