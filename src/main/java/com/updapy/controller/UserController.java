package com.updapy.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.updapy.form.ajax.JsonResponse;
import com.updapy.form.model.RegisterEarlyUser;
import com.updapy.form.model.RegisterUser;
import com.updapy.form.validator.RegisterEarlyUserCustomValidator;
import com.updapy.form.validator.RegisterUserCustomValidator;
import com.updapy.service.UserService;
import com.updapy.util.MessageUtil;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	MessageUtil messageUtil;

	@Autowired
	private UserService userService;

	@Autowired
	private RegisterUserCustomValidator registerUserCustomValidator;

	@Autowired
	private RegisterEarlyUserCustomValidator registerEarlyUserCustomValidator;

	@InitBinder("registerEarlyUser")
	private void initBinderEarly(WebDataBinder binder) {
		binder.addValidators(registerEarlyUserCustomValidator);
	}

	@InitBinder("registerUser")
	private void initBinder(WebDataBinder binder) {
		binder.addValidators(registerUserCustomValidator);
	}

	@RequestMapping(value = "register-early", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse registerEarly(@Valid @RequestBody RegisterEarlyUser registerEarlyUser, BindingResult result) {
		JsonResponse jsonResponse = new JsonResponse();
		if (result.hasErrors()) {
			jsonResponse.setStatus("FAIL");
			List<String> errors = new ArrayList<String>();
			for (ObjectError error: result.getAllErrors()) {
				errors.add(messageUtil.getSimpleMessage(error));
			}
			jsonResponse.setResult(errors);
		} else {
			userService.registerEarly(registerEarlyUser.getEmail());
			jsonResponse.setStatus("SUCCESS");
			jsonResponse.setResult(Arrays.asList(messageUtil.getSimpleMessage("early.interest.add.confirm")));
		}
		return jsonResponse;
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String register(@Valid RegisterUser registerUser, BindingResult result) {

		if (result.hasErrors()) {
			if (registerUser.getRequestUri().equals("signGlobal")) {
				return "sign";
			}
			return "sign-up";
		} else {

			// TODO: Encrypt password

			// TODO: Save user

			// TODO: Send activation email
			
			return "sign-up-activate";
		}
	}

	@RequestMapping(value = "activate")
	public String sendActivationEmail(@RequestParam(value = "email", required = true) String email) {
		// TODO : check account is not activated
		// send an email
		return "sign-up-activate-resend";
	}

	@RequestMapping(value = "reset", method = RequestMethod.POST)
	public String resetPassword() {
		// TODO : check account is activated
		// send an email
		return "sign-in-reset";
	}

}
