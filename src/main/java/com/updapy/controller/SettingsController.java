package com.updapy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.updapy.form.ajax.JsonResponse;
import com.updapy.form.model.ChangePasswordUser;
import com.updapy.form.model.DeleteAccount;
import com.updapy.form.model.UpdateSettings;
import com.updapy.model.User;
import com.updapy.service.SettingsService;
import com.updapy.service.UserService;
import com.updapy.service.security.SecurityUtils;
import com.updapy.util.JsonResponseUtils;

@Controller
@RequestMapping("settings")
public class SettingsController {

	@Autowired
	private JsonResponseUtils jsonResponseUtils;

	@Autowired
	private SettingsService settingsService;

	@Autowired
	private UserService userService;

	@Autowired
	private Validator changePasswordUserCustomValidator;

	@InitBinder("changePasswordUser")
	private void initBinderChangePassword(WebDataBinder binder) {
		binder.addValidators(changePasswordUserCustomValidator);
	}

	@RequestMapping({ "/", "" })
	public ModelAndView settingsPage() {
		ModelAndView modelAndView = new ModelAndView("settings");
		modelAndView.addObject("updateSettings", settingsService.getCurrentSettings());
		modelAndView.addObject("changePasswordUser", new ChangePasswordUser());
		modelAndView.addObject("deleteAccount", new DeleteAccount());
		return modelAndView;
	}

	@RequestMapping(value = "update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse update(@Valid @RequestBody UpdateSettings updateSettings, BindingResult result) {
		if (result.hasErrors()) {
			return jsonResponseUtils.buildFailedJsonResponseFromErrorObject(result.getAllErrors());
		} else {
			String currentName = userService.getCurrentUser().getName();
			User user = settingsService.updateCurrentSettings(updateSettings);
			String newName = (user.getName() == null) ? user.getEmail() : user.getName();
			if (!StringUtils.equals(currentName, newName)) {
				SecurityUtils.reloadUsername(newName);
			}
			return jsonResponseUtils.buildSuccessfulJsonResponse("settings.save.confirm");
		}
	}

	@RequestMapping(value = "updapte/password", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse changePassword(@Valid @RequestBody ChangePasswordUser changePasswordUser, BindingResult result) {
		if (result.hasErrors()) {
			return jsonResponseUtils.buildFailedJsonResponseFromErrorObject(result.getAllErrors());
		} else {
			userService.updateCurrentPassword(changePasswordUser.getNewPassword());
			return jsonResponseUtils.buildSuccessfulJsonResponse("settings.profile.changePassword.confirm");
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String deleteAccount(DeleteAccount deleteAccount, HttpServletRequest request, HttpServletResponse response) {
		settingsService.addFeedback(deleteAccount.getFeedback());
		boolean isDeleted = userService.deleteCurrentUser();
		if (isDeleted) {
			SecurityUtils.logout(request, response);
			return "delete-account-complete";
		} else {
			return "error-delete";
		}
	}

}
