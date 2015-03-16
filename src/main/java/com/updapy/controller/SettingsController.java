package com.updapy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
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
import com.updapy.form.model.ChangeEmailUser;
import com.updapy.form.model.ChangePasswordUser;
import com.updapy.form.model.DeleteAccount;
import com.updapy.form.model.UpdateSettings;
import com.updapy.model.User;
import com.updapy.service.EmailSenderService;
import com.updapy.service.SettingsService;
import com.updapy.service.UserService;
import com.updapy.service.security.SecurityUtils;
import com.updapy.util.JsonResponseUtils;

@Controller
@RequestMapping("/settings")
public class SettingsController {

	@Autowired
	private JsonResponseUtils jsonResponseUtils;

	@Autowired
	private SettingsService settingsService;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private Validator changePasswordUserCustomValidator;
	
	@Autowired
	private Validator changeEmailUserCustomValidator;
	
	@Autowired
	PersistentTokenBasedRememberMeServices springSocialSecurityRememberMeServices;
	
	@InitBinder("changePasswordUser")
	private void initBinderChangePassword(WebDataBinder binder) {
		binder.addValidators(changePasswordUserCustomValidator);
	}

	@InitBinder("changeEmailUser")
	private void initBinderChangeEmail(WebDataBinder binder) {
		binder.addValidators(changeEmailUserCustomValidator);
	}


	@RequestMapping({ "/", "" })
	public ModelAndView settingsPage() {
		User user = userService.getCurrentUserWithSettings();
		if (user == null) {
			return new ModelAndView("welcome");
		}
		ModelAndView modelAndView = new ModelAndView("settings");
		modelAndView.addObject("updateSettings", settingsService.getSettings(user));
		modelAndView.addObject("nbNotifications", userService.getNbNotifications(user));
		modelAndView.addObject("rssKey", user.getRssKey());
		modelAndView.addObject("changePasswordUser", new ChangePasswordUser());
		modelAndView.addObject("changeEmailUser", new ChangeEmailUser());
		modelAndView.addObject("deleteAccount", new DeleteAccount());
		return modelAndView;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse update(@Valid @RequestBody UpdateSettings updateSettings, BindingResult result) {
		if (result.hasErrors()) {
			return jsonResponseUtils.buildFailedJsonResponseFromErrorObject(result.getAllErrors());
		} else {
			User user = userService.getCurrentUserWithSettings();
			String currentName = user.getName();
			user = settingsService.updateSettings(user, updateSettings);
			String newName = (user.getName() == null) ? user.getEmail() : user.getName();
			if (!StringUtils.equals(currentName, newName)) {
				SecurityUtils.reloadUsername(newName);
			}
			return jsonResponseUtils.buildSuccessfulJsonResponse("settings.save.confirm");
		}
	}

	@RequestMapping(value = "/update/password", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse changePassword(@Valid @RequestBody ChangePasswordUser changePasswordUser, BindingResult result) {
		if (result.hasErrors()) {
			return jsonResponseUtils.buildFailedJsonResponseFromErrorObject(result.getAllErrors());
		} else {
			userService.updatePassword(userService.getCurrentUserLight(), changePasswordUser.getNewPassword());
			return jsonResponseUtils.buildSuccessfulJsonResponse("settings.profile.changePassword.confirm");
		}
	}

	@RequestMapping(value = "/update/email", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse changeEmail(@Valid @RequestBody ChangeEmailUser changeEmailUser, BindingResult result) {
		if (result.hasErrors()) {
			return jsonResponseUtils.buildFailedJsonResponseFromErrorObject(result.getAllErrors());
		} else {
			User user = userService.getCurrentUserLight();
			userService.updateEmail(user, changeEmailUser.getNewEmail());
			emailSenderService.sendActivationLink(user.getEmail(), user.getAccountKey(), user.getLangEmail());
			return jsonResponseUtils.buildSuccessfulJsonResponse("settings.profile.changeEmail.confirm");
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteAccount(DeleteAccount deleteAccount, HttpServletRequest request, HttpServletResponse response) {
		settingsService.addFeedback(deleteAccount.getFeedback());
		boolean isDeleted = userService.delete(userService.getCurrentUserLight());
		if (isDeleted) {
			SecurityUtils.logout(request, response, springSocialSecurityRememberMeServices);
			return "delete-account-complete";
		} else {
			return "error-delete";
		}
	}

}
