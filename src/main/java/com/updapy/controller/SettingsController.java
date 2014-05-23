package com.updapy.controller;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.updapy.form.ajax.JsonResponse;
import com.updapy.form.model.UpdateSettings;
import com.updapy.model.User;
import com.updapy.service.SettingsService;
import com.updapy.service.UserService;
import com.updapy.service.security.SecurityUtils;
import com.updapy.util.JsonResponseUtils;

@Controller
@Transactional
@RequestMapping("settings")
public class SettingsController {

	@Autowired
	JsonResponseUtils jsonResponseUtils;

	@Autowired
	DozerBeanMapper dozerMapper;

	@Autowired
	private SettingsService settingsService;

	@Autowired
	private UserService userService;

	@RequestMapping({ "/", "" })
	public ModelAndView settingsPage() {
		return new ModelAndView("settings", "updateSettings", settingsService.getSettings(userService.getCurrentUser()));
	}

	@RequestMapping(value = "update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse update(@Valid @RequestBody UpdateSettings updateSettings, BindingResult result) {
		if (result.hasErrors()) {
			return jsonResponseUtils.buildFailedJsonResponseFromErrorObject(result.getAllErrors());
		} else {
			User user = userService.getCurrentUser();
			String currentName = user.getName();
			settingsService.updateSettings(user, updateSettings);
			String newName = (user.getName() == null) ? user.getEmail() : user.getName();
			if (!StringUtils.equals(currentName, newName)) {
				SecurityUtils.reloadUsername(newName);
			}
			return jsonResponseUtils.buildSuccessfulJsonResponse("settings.save.confirm");
		}
	}

}
