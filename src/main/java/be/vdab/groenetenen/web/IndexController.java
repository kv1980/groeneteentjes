package be.vdab.groenetenen.web;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
class IndexController {
	private static final String VIEW = "index";

	private String begroeting() {
		int uur = LocalDateTime.now().getHour();
		if (uur >= 6 && uur < 12) {
			return "goedeMorgen";
		}
		if (uur >= 12 && uur < 18) {
			return "goedeMiddag";
		}
		return "goedeAvond";
	}

	@GetMapping
	ModelAndView index() {
		return new ModelAndView(VIEW).addObject("begroeting", begroeting());
	}
}