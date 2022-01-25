package com.capybara.CapybaraCampusCrawlBackend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

	@GetMapping("/greeting")
	public String greeting() {

		return "greeting";
	}
	
}
