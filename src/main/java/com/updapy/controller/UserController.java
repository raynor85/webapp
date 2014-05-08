package com.updapy.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.dozer.DozerBeanMapper;
import org.joda.time.DateTime;
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
import org.springframework.web.servlet.ModelAndView;

import com.updapy.form.ajax.JsonResponse;
import com.updapy.form.model.RegisterEarlyUser;
import com.updapy.form.model.RegisterUser;
import com.updapy.form.model.ResetUser;
import com.updapy.form.model.ResetUserEmail;
import com.updapy.form.validator.RegisterEarlyUserCustomValidator;
import com.updapy.form.validator.RegisterUserCustomValidator;
import com.updapy.form.validator.ResetUserCustomValidator;
import com.updapy.form.validator.ResetUserEmailCustomValidator;
import com.updapy.model.User;
import com.updapy.service.UserService;
import com.updapy.util.MessageUtil;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	MessageUtil messageUtil;

	@Autowired
	DozerBeanMapper dozerMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private RegisterUserCustomValidator registerUserCustomValidator;

	@Autowired
	private RegisterEarlyUserCustomValidator registerEarlyUserCustomValidator;

	@Autowired
	private ResetUserEmailCustomValidator resetUserEmailCustomValidator;
	
	@Autowired
	private ResetUserCustomValidator resetUserCustomValidator;
	
	@InitBinder("registerEarlyUser")
	private void initBinderEarly(WebDataBinder binder) {
		binder.addValidators(registerEarlyUserCustomValidator);
	}

	@InitBinder("registerUser")
	private void initBinder(WebDataBinder binder) {
		binder.addValidators(registerUserCustomValidator);
	}

	@InitBinder("resetUserEmail")
	private void initBinderResetPassword(WebDataBinder binder) {
		binder.addValidators(resetUserEmailCustomValidator);
	}
	
	@InitBinder("resetUser")
	private void initBinderResetPasswordConfirm(WebDataBinder binder) {
		binder.addValidators(resetUserCustomValidator);
	}

	@RequestMapping(value = "register-early", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse registerEarly(@Valid @RequestBody RegisterEarlyUser registerEarlyUser, BindingResult result) {
		JsonResponse jsonResponse = new JsonResponse();
		if (result.hasErrors()) {
			jsonResponse.setStatus("FAIL");
			List<String> errors = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
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
	public ModelAndView register(@Valid RegisterUser registerUser, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("", "email", registerUser.getEmail());
		if (result.hasErrors()) {
			if (registerUser.getRequestUri().equals("signGlobal")) {
				modelAndView.setViewName("sign");
				modelAndView.addObject("resetUserEmail", new ResetUserEmail());
				return modelAndView;
			}
			modelAndView.setViewName("sign-up");
			return modelAndView;
		} else {
			User user = userService.register(dozerMapper.map(registerUser, User.class));
			sendActivationLinkEmail(user.getEmail(), user.getAccount().getActivation().getKey());
			modelAndView.setViewName("sign-up-activate");
			return modelAndView;
		}
	}

	@RequestMapping(value = "activate/send")
	public ModelAndView sendActivationEmail(@RequestParam(value = "email", required = true) String email) {
		ModelAndView modelAndView = new ModelAndView("", "email", email);
		User user = userService.findByEmail(email);
		if (user == null // user not found
				|| user.getAccount().getActivation().isActive() // account already active
		) {
			modelAndView.setViewName("error-activate-send");
			return modelAndView;
		}
		String newKey = userService.generateNewKey(user);
		sendActivationLinkEmail(email, newKey);
		modelAndView.setViewName("sign-up-activate-resend");
		return modelAndView;
	}

	private void sendActivationLinkEmail(String email, String key) {
		// TODO send email here with a dedicated service
		String link = messageUtil.getSimpleMessage("application.root.url") + "/user/activate?email=" + email + "&key=" + key;
		System.out.println("Send an email with the link: " + link);
	}

	@RequestMapping(value = "activate")
	public String activate(@RequestParam(value = "email", required = true) String email, @RequestParam(value = "key", required = true) String key) {
		User user = userService.findByEmail(email);
		if (user == null // user not found
				|| user.getAccount().getActivation().isActive() // account already active
				|| !user.getAccount().getActivation().getKey().equals(key) // different key
				|| new DateTime().plusHours(1).isBefore(new DateTime(user.getAccount().getActivation().getGenerationKeyDate())) // key is expired (validity is 1h)
		) {
			return "error-activate-link";
		}
		userService.activate(user);
		return "sign-up-complete";
	}

	@RequestMapping(value = "reset/send", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse sendResetPasswordEmail(@Valid @RequestBody ResetUserEmail resetUserEmail, BindingResult result) {
		JsonResponse jsonResponse = new JsonResponse();
		if (result.hasErrors()) {
			jsonResponse.setStatus("FAIL");
			List<String> errors = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errors.add(messageUtil.getSimpleMessage(error));
			}
			jsonResponse.setResult(errors);
		} else {
			String email = resetUserEmail.getEmail();
			String newKey = userService.generateNewKey(userService.findByEmail(email));
			sendResetPasswordEmail(email, newKey);
			jsonResponse.setStatus("SUCCESS");
			jsonResponse.setResult(Arrays.asList(messageUtil.getSimpleMessage("sign.in.forgot.send.confirm")));
		}
		return jsonResponse;
	}

	private void sendResetPasswordEmail(String email, String key) {
		// TODO send email here with a dedicated service
		String link = messageUtil.getSimpleMessage("application.root.url") + "/user/resetpassword?email=" + email + "&key=" + key;
		System.out.println("Send an email with the link: " + link);
	}

	@RequestMapping(value = "resetpassword")
	public ModelAndView resetPassword(@RequestParam(value = "email", required = true) String email, @RequestParam(value = "key", required = true) String key) {
		User user = userService.findByEmail(email);
		if (user == null // user not found
				|| !user.getAccount().getActivation().getKey().equals(key) // different key
				|| new DateTime().plusHours(1).isBefore(new DateTime(user.getAccount().getActivation().getGenerationKeyDate())) // key is expired (validity is 1h)
		) {
			return new ModelAndView("error-reset-link");
		}
		ModelAndView modelAndView = new ModelAndView("sign-in-reset");
		ResetUser resetUser = new ResetUser();
		resetUser.setEmail(email);
		resetUser.setKey(key);
		modelAndView.addObject("resetUser", resetUser);
		return modelAndView;
	}

	@RequestMapping(value = "reset", method = RequestMethod.POST)
	public String resetPasswordConfirm(@Valid ResetUser resetUser, BindingResult result) {
		if (result.hasErrors()) {
			return "sign-in-reset";
		} else {
			User user = userService.findByEmail(resetUser.getEmail());
			userService.updatePassword(user, resetUser.getNewPassword());
			return "reset-complete";
		}
	}
}
