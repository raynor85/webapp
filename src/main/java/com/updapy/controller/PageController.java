package com.updapy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.updapy.form.model.RegisterEarlyUser;
import com.updapy.form.model.RegisterUser;

@Controller
public class PageController {

	@RequestMapping("/")
	public ModelAndView welcomePage() {
		return new ModelAndView("welcome", "registerEarlyUser", new RegisterEarlyUser());
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
	public ModelAndView signPage() {
		return new ModelAndView("sign", "registerUser", new RegisterUser());
	}

	@RequestMapping("sign-up")
	public ModelAndView signupPage() {
		return new ModelAndView("sign-up", "registerUser", new RegisterUser());
	}

	@RequestMapping("sign-up-activate")
	public String signupActivatePage() {
		return "sign-up-activate";
	}
}
