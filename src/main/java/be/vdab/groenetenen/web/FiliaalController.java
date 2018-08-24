package be.vdab.groenetenen.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.vdab.groenetenen.entities.Filiaal;
import be.vdab.groenetenen.services.FiliaalService;

@Controller
@RequestMapping(path = "filialen", produces = MediaType.TEXT_HTML_VALUE)
public class FiliaalController {
	private static final String VAN_TOT_POSTCODE_VIEW = "filialen/vantotpostcode";
	private static final String FILIAAL_VIEW = "filialen/filiaal";
	private static final String PER_ID_VIEW = "filialen/perid";
	private static final String REDIRECT_FILIAAL_NIET_GEVONDEN = "redirect:/";
	private static final String REDIRECT_NA_AFSCHRIJVEN = "redirect:/filialen/{id}";
	private final FiliaalService filiaalService;

	public FiliaalController(FiliaalService filiaalService) {
		this.filiaalService = filiaalService;
	}

	@GetMapping("vantotpostcode")
	ModelAndView vanTotPostcode() {
		VanTotPostcodeForm vanTotPostcodeForm = new VanTotPostcodeForm();
		vanTotPostcodeForm.setVan(1000);
		vanTotPostcodeForm.setTot(9999);
		return new ModelAndView(VAN_TOT_POSTCODE_VIEW).addObject(vanTotPostcodeForm);
	}

	@GetMapping(params = { "van", "tot" })
	ModelAndView vanTotPostcode(@Valid VanTotPostcodeForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView(VAN_TOT_POSTCODE_VIEW);
		}
		List<Filiaal> filialen = filiaalService.findByPostcode(form.getVan(), form.getTot());
		return new ModelAndView(VAN_TOT_POSTCODE_VIEW)
				.addObject("filialen",filialen)
				.addObject("aantalFilialen",filialen.size());
	}

	@GetMapping("{filiaal}")
	ModelAndView read(@PathVariable Optional<Filiaal> filiaal, RedirectAttributes redirectAttributes) {
		if (filiaal.isPresent()) {
			return new ModelAndView(FILIAAL_VIEW).addObject(filiaal.get());
		}
		redirectAttributes.addAttribute("fout", "Foutboodschap: filiaal werd niet gevonden.");
		return new ModelAndView(REDIRECT_FILIAAL_NIET_GEVONDEN);
	}
	
	@GetMapping("perid")
	String findById() {
		return PER_ID_VIEW;
	}
	
	@PostMapping("{id}/afschrijven")
	String afschrijven(@PathVariable long id,RedirectAttributes redirectAttributes) {
		filiaalService.afschrijven(id);
		redirectAttributes.addAttribute("id",id);
		return REDIRECT_NA_AFSCHRIJVEN;
	}
}