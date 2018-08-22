package be.vdab.groenetenen.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import be.vdab.groenetenen.web.VanTotPostcodeForm;

public class VanTotPostcodeFormVanKleinerDanOfGelijkAanTotValidator 
	implements ConstraintValidator<VanTotPostcodeFormVanKleinerDanOfGelijkAanTot,VanTotPostcodeForm> {
	
	@Override
	public void initialize(VanTotPostcodeFormVanKleinerDanOfGelijkAanTot arg0) {
	}

	@Override
	public boolean isValid(VanTotPostcodeForm form, ConstraintValidatorContext context) {
		if (form.getVan() == null || form.getTot() == null) {
			return true;
		}
		return form.getVan() <= form.getTot();
	}
}