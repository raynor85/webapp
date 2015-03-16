package com.updapy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.updapy.form.model.RegisterUser;
import com.updapy.form.model.ResetUserEmail;
import com.updapy.model.User;
import com.updapy.service.UserService;

@Controller
public class PageController {

	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public String welcomePage() {
		return "welcome";
	}

	@RequestMapping("/error/404")
	public ModelAndView error404Page() {
		return addNotifications(new ModelAndView("error-404"));
	}

	@RequestMapping("/error/403")
	public ModelAndView error403Page() {
		return addNotifications(new ModelAndView("error-403"));
	}

	@RequestMapping("/error")
	public ModelAndView errorPage() {
		return addNotifications(new ModelAndView("error"));
	}

	@RequestMapping("/faq")
	public ModelAndView faqPage() {
		return addNotifications(new ModelAndView("faq"));
	}

	@RequestMapping("/privacy")
	public ModelAndView privacyPage() {
		return addNotifications(new ModelAndView("privacy"));
	}

	@RequestMapping({ "/sign", "/signin" })
	public ModelAndView signPage() {
		ModelAndView modelAndView = new ModelAndView("sign");
		modelAndView.addObject("resetUserEmail", new ResetUserEmail());
		modelAndView.addObject("registerUser", new RegisterUser());
		return addNotifications(modelAndView);
	}

	@RequestMapping({ "/sign-up", "/signup" })
	public ModelAndView signupPage() {
		return new ModelAndView("sign-up", "registerUser", new RegisterUser());
	}

	@RequestMapping("/signup/activate")
	public String signupActivatePage() {
		return "sign-up-activate";
	}

	@RequestMapping("/thanks")
	public ModelAndView thanksPage() {
		return addNotifications(new ModelAndView("thanks"));
	}

	private ModelAndView addNotifications(ModelAndView modelAndView) {
		User user = userService.getCurrentUserLight();
		if (user != null) {
			modelAndView.addObject("nbNotifications", userService.getNbNotifications(user));
			modelAndView.addObject("rssKey", user.getRssKey());
		}
		return modelAndView;
	}
}
