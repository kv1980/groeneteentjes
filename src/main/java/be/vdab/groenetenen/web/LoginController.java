package be.vdab.groenetenen.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login")
public class LoginController {
	private static final String VIEW="login";
	
	@GetMapping
	String login() {
		return VIEW;
	}
}
