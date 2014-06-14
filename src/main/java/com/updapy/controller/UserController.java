package com.updapy.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.updapy.form.ajax.JsonResponse;
import com.updapy.form.model.RegisterEarlyUser;
import com.updapy.form.model.RegisterUser;
import com.updapy.form.model.ResetUser;
import com.updapy.form.model.ResetUserEmail;
import com.updapy.model.User;
import com.updapy.service.MailSenderService;
import com.updapy.service.UserService;
import com.updapy.service.security.SecurityUtils;
import com.updapy.util.DozerHelper;
import com.updapy.util.JsonResponseUtils;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private JsonResponseUtils jsonResponseUtils;

	@Autowired
	private DozerHelper dozerHelper;

	@Autowired
	private UserService userService;

	@Autowired
	private MailSenderService mailSenderService;

	@Autowired
	private Validator registerUserCustomValidator;

	@Autowired
	private Validator registerEarlyUserCustomValidator;

	@Autowired
	private Validator resetUserEmailCustomValidator;

	@Autowired
	private Validator resetUserCustomValidator;

	@InitBinder("/registerEarlyUser")
	private void initBinderEarly(WebDataBinder binder) {
		binder.addValidators(registerEarlyUserCustomValidator);
	}

	@InitBinder("/registerUser")
	private void initBinderRegisterUser(WebDataBinder binder) {
		binder.addValidators(registerUserCustomValidator);
	}

	@InitBinder("/resetUserEmail")
	private void initBinderResetPassword(WebDataBinder binder) {
		binder.addValidators(resetUserEmailCustomValidator);
	}

	@InitBinder("/resetUser")
	private void initBinderResetPasswordConfirm(WebDataBinder binder) {
		binder.addValidators(resetUserCustomValidator);
	}

	@RequestMapping(value = "/locale", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody
	JsonResponse changeLocale(@RequestBody String locale) {
		if (StringUtils.isBlank(locale) || (!(locale.equals("en")) && !(locale.equals("fr")))) {
			return jsonResponseUtils.buildFailedJsonResponse("language.change.error");
		}
		userService.changeLocale(new Locale(locale));
		return jsonResponseUtils.buildSuccessfulJsonResponse("language.change.confirm");
	}

	@RequestMapping(value = "/register/early", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse registerEarly(@Valid @RequestBody RegisterEarlyUser registerEarlyUser, BindingResult result) {
		if (result.hasErrors()) {
			return jsonResponseUtils.buildFailedJsonResponseFromErrorObject(result.getAllErrors());
		} else {
			userService.registerEarly(registerEarlyUser.getEmail());
			return jsonResponseUtils.buildSuccessfulJsonResponse("early.interest.add.confirm");
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerNormal(@Valid RegisterUser registerUser, BindingResult result) {
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
			User user = userService.register(dozerHelper.map(registerUser, User.class));
			mailSenderService.sendActivationLink(user.getEmail(), user.getAccountKey(), user.getLangEmail());
			modelAndView.setViewName("sign-up-activate");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerSocial(WebRequest request, ProviderSignInUtils providerSignInUtils) {
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
		User user = userService.registerSocial(connection);
		if (user != null) {
			providerSignInUtils.doPostSignUp(user.getEmail(), request);
			SecurityUtils.logInSocialUser(user);
			return "sign-up-social-complete";
		}
		return "error-social";
	}

	@RequestMapping(value = "/activate/send")
	public ModelAndView sendActivationEmail(@RequestParam(value = "email", required = true) String email) {
		ModelAndView modelAndView = new ModelAndView("", "email", email);
		User user = userService.findByEmail(email);
		if (user == null // user not found
				|| user.isActive() // account already active
		) {
			modelAndView.setViewName("error-activate-send");
			return modelAndView;
		}
		String newKey = userService.generateNewAccountKey(user);
		mailSenderService.sendActivationLink(email, newKey, user.getLangEmail());
		modelAndView.setViewName("sign-up-activate-resend");
		return modelAndView;
	}

	@RequestMapping(value = "/activate")
	public String activate(@RequestParam(value = "email", required = true) String email, @RequestParam(value = "key", required = true) String key) {
		User user = userService.findByEmail(email);
		if (user == null // user not found
				|| user.isActive() // account already active
				|| !user.getAccountKey().equals(key) // different key
				|| new DateTime().plusHours(1).isBefore(new DateTime(user.getGenerationAccountKeyDate())) // key is expired (validity is 1h)
		) {
			return "error-activate-link";
		}
		userService.activate(user);
		return "sign-up-complete";
	}

	@RequestMapping(value = "/reset/send", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse sendResetPasswordEmail(@Valid @RequestBody ResetUserEmail resetUserEmail, BindingResult result) {
		if (result.hasErrors()) {
			return jsonResponseUtils.buildFailedJsonResponseFromErrorObject(result.getAllErrors());
		} else {
			String email = resetUserEmail.getEmail();
			User user = userService.findByEmail(email);
			if (user.isSocialUser()) { // user is social, so no password
				return jsonResponseUtils.buildFailedJsonResponse("NotFound.resetUserEmail.email");
			}
			String newKey = userService.generateNewAccountKey(user);
			mailSenderService.sendResetPasswordLink(email, newKey, user.getLangEmail());
			return jsonResponseUtils.buildSuccessfulJsonResponse("sign.in.forgot.send.confirm");
		}
	}

	@RequestMapping(value = "/reset/password")
	public ModelAndView resetPassword(@RequestParam(value = "email", required = true) String email, @RequestParam(value = "key", required = true) String key) {
		User user = userService.findByEmail(email);
		if (user == null // user not found
				|| !user.getAccountKey().equals(key) // different key
				|| new DateTime().plusHours(1).isBefore(new DateTime(user.getGenerationAccountKeyDate())) // key is expired (validity is 1h)
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

	@RequestMapping(value = "/reset", method = RequestMethod.POST)
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
