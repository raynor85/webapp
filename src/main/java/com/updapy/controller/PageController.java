package com.updapy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping("/")
	public String welcomePage() { 
		return "welcome";
	}
	 
	@RequestMapping("error")
	public String errorPage() {
		return "error";
	}
	
	@RequestMapping("faq")
	public String faqPage() {
		return "faq";
	}

	@RequestMapping("privacy")
	public String privacyPage() {
		return "privacy";
	}

	@RequestMapping("sign")
	public String signPage() {
		return "sign";
	}

	@RequestMapping("sign-up")
	public String signupPage() {
		return "sign-up";
	}
	
	@RequestMapping("sign-up-confirm")
	public String signupConfirmPage() {
		return "sign-up-confirm";
	}
}
