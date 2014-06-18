package com.updapy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.updapy.form.model.RegisterEarlyUser;
import com.updapy.form.model.RegisterUser;
import com.updapy.form.model.ResetUserEmail;
import com.updapy.service.UserService;

@Controller
public class PageController {

	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public ModelAndView welcomePage() {
		return addNbNotifications(new ModelAndView("welcome", "registerEarlyUser", new RegisterEarlyUser()));
	}

	@RequestMapping("/error/404")
	public ModelAndView error404Page() {
		return addNbNotifications(new ModelAndView("error-404"));
	}

	@RequestMapping("/error/403")
	public ModelAndView error403Page() {
		return addNbNotifications(new ModelAndView("error-403"));
	}

	@RequestMapping("/error")
	public ModelAndView errorPage() {
		return addNbNotifications(new ModelAndView("error"));
	}

	@RequestMapping("/faq")
	public ModelAndView faqPage() {
		return addNbNotifications(new ModelAndView("faq"));
	}

	@RequestMapping("/privacy")
	public ModelAndView privacyPage() {
		return addNbNotifications(new ModelAndView("privacy"));
	}

	@RequestMapping({ "/sign", "/signin" })
	public ModelAndView signPage() {
		ModelAndView modelAndView = new ModelAndView("sign");
		modelAndView.addObject("resetUserEmail", new ResetUserEmail());
		modelAndView.addObject("registerUser", new RegisterUser());
		return addNbNotifications(modelAndView);
	}

	@RequestMapping({ "/sign-up", "/signup" })
	public ModelAndView signupPage() {
		return new ModelAndView("sign-up", "registerUser", new RegisterUser());
	}

	@RequestMapping("/signup/activate")
	public String signupActivatePage() {
		return "sign-up-activate";
	}

	private ModelAndView addNbNotifications(ModelAndView modelAndView) {
		modelAndView.addObject("nbNotifications", userService.getNbNotifications(userService.getCurrentUserLight()));
		return modelAndView;
	}
}
