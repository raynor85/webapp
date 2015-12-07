package com.updapy.controller;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javassist.Modifier;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.updapy.form.model.RegisterUser;
import com.updapy.form.model.ResetUserEmail;
import com.updapy.model.User;
import com.updapy.service.ApplicationService;
import com.updapy.service.UserService;

@Controller
public class PageController {

	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationService applicationService;

	private static final List<String> animations = Arrays.asList("tada", "bounce", "flash", "rubberBand", "shake", "swing", "wobble");
	private static final Random randomGenerator = new Random();

	@RequestMapping("/")
	public ModelAndView welcomePage() {
		ModelAndView modelAndView = new ModelAndView("welcome");
		modelAndView.addObject("numberOfApplicationsActive", applicationService.getNumberOfApplicationsActive());
		modelAndView.addObject("shareIconEffect", animations.get(randomGenerator.nextInt(animations.size())));
		return addNotifications(modelAndView);
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

	@PostConstruct
	private void init() {
		// TODO: Remove this temporary hack for the login of facebook when there is a new release of spring social facebook
		try {
			String[] fieldsToMap = { "id", "about", "age_range", "address", "bio", "birthday", "context", "cover", "currency", "devices", "education", "email", "favorite_athletes", "favorite_teams", "first_name", "gender", "hometown", "inspirational_people", "installed", "install_type", "is_verified", "languages", "last_name", "link", "locale", "location", "meeting_for", "middle_name", "name", "name_format", "political", "quotes", "payment_pricepoints", "relationship_status", "religion",
					"security_settings", "significant_other", "sports", "test_group", "timezone", "third_party_id", "updated_time", "verified", "viewer_can_send_gift", "website", "work" };

			Field field = Class.forName("org.springframework.social.facebook.api.UserOperations").getDeclaredField("PROFILE_FIELDS");
			field.setAccessible(true);

			Field modifiers = field.getClass().getDeclaredField("modifiers");
			modifiers.setAccessible(true);
			modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			field.set(null, fieldsToMap);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
