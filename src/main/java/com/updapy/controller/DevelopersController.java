package com.updapy.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("developers")
public class DevelopersController {

	@Autowired
	JsonResponseUtils jsonResponseUtils;

	@Autowired
	private UserService userService;

	@RequestMapping({ "/", "" })
	public ModelAndView dashboardPage() {
		ResetUserApiKey resetUserApiKey = new ResetUserApiKey();
		User user = userService.getCurrentUser();
		if (user != null) {
			resetUserApiKey.setApiKey(user.getApiKey());
		}
		return new ModelAndView("developers", "resetUserApiKey", resetUserApiKey);
	}

	@RequestMapping(value = "resetApiKey", method = RequestMethod.POST)
	public @ResponseBody
	JsonResponse resetApiKey() {
		userService.generateCurrentNewApiKey();
		return jsonResponseUtils.buildSuccessfulJsonResponse("developers.api.apiKey.reset.confirm");
	}

	@RequestMapping(value = "getApiKey")
	public @ResponseBody
	String getApiKey() {
		return userService.getCurrentUser().getApiKey();
	}

}
