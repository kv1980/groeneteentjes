package be.vdab.groenetenen.web;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.groenetenen.exceptions.KanKoersNietLezenException;
import be.vdab.groenetenen.services.EuroService;

@Controller
@RequestMapping("euro")
class EuroController {
	private static final String VIEW = "euro/naarDollar";
	private final EuroService euroService;

	EuroController(EuroService euroService) {
		this.euroService = euroService;
	}

	@GetMapping("{euro}/naardollar")
	ModelAndView naarDollar(@PathVariable BigDecimal euro) {
		ModelAndView modelAndView = new ModelAndView(VIEW);
		try {
			modelAndView.addObject(new EuroDollar(euro, euroService.naarDollar(euro)));
		} catch (KanKoersNietLezenException ex) {
			modelAndView.addObject("fout", "Kan koers niet lezen");
		}
		return modelAndView;
	}
}