package be.vdab.groenetenen.restclients;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

class Rates {
	@JsonProperty("USD")
	private BigDecimal usdValue;
	
	public BigDecimal getUsdValue() {
		return usdValue;
	}
	public void setUsdValue(BigDecimal usdValue) {
		this.usdValue = usdValue;
	}
}
