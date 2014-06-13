package com.updapy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.updapy.form.model.RegisterEarlyUser;
import com.updapy.form.model.RegisterUser;
import com.updapy.form.model.ResetUserEmail;

@Controller
public class PageController {

	@RequestMapping("/")
	public ModelAndView welcomePage() {
		return new ModelAndView("welcome", "registerEarlyUser", new RegisterEarlyUser());
	}

	@RequestMapping("/error/404")
	public String error404Page() {
		return "error-404";
	}

	@RequestMapping("/error/403")
	public String error403Page() {
		return "error-403";
	}

	@RequestMapping("/error")
	public String errorPage() {
		return "error";
	}

	@RequestMapping("/faq")
	public String faqPage() {
		return "faq";
	}

	@RequestMapping("/privacy")
	public String privacyPage() {
		return "privacy";
	}

	@RequestMapping({ "/sign", "/signin" })
	public ModelAndView signPage() {
		ModelAndView modelAndView = new ModelAndView("sign");
		modelAndView.addObject("resetUserEmail", new ResetUserEmail());
		modelAndView.addObject("registerUser", new RegisterUser());
		return modelAndView;
	}

	@RequestMapping({ "/sign-up", "/signup" })
	public ModelAndView signupPage() {
		return new ModelAndView("sign-up", "registerUser", new RegisterUser());
	}

	@RequestMapping("/signup/activate")
	public String signupActivatePage() {
		return "sign-up-activate";
	}
}
