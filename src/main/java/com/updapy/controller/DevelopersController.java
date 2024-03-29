package com.updapy.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.updapy.form.ajax.JsonResponse;
import com.updapy.form.model.ResetUserApiKey;
import com.updapy.model.User;
import com.updapy.service.UserService;
import com.updapy.util.JsonResponseUtils;

@Controller
@RequestMapping("/developers")
public class DevelopersController {

	@Inject
	private JsonResponseUtils jsonResponseUtils;

	@Inject
	private UserService userService;

	@RequestMapping({ "/", "" })
	public ModelAndView dashboardPage() {
		ResetUserApiKey resetUserApiKey = new ResetUserApiKey();
		User user = userService.getCurrentUserLight();
		ModelAndView modelAndView = new ModelAndView("developers", "resetUserApiKey", resetUserApiKey);
		if (user != null) {
			resetUserApiKey.setApiKey(user.getApiKey());
			modelAndView.addObject("nbNotifications", userService.getNbNotifications(user));
			modelAndView.addObject("rssKey", user.getRssKey());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/apikey/reset", method = RequestMethod.POST)
	public @ResponseBody
	JsonResponse resetApiKey() {
		userService.generateNewApiKey(userService.getCurrentUserLight());
		return jsonResponseUtils.buildSuccessfulJsonResponse("developers.api.apiKey.reset.confirm");
	}

	@RequestMapping(value = "/apikey/get")
	public @ResponseBody
	String getApiKey() {
		return userService.getCurrentUserLight().getApiKey();
	}

}
