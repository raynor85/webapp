package com.updapy.controller;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.updapy.service.PersonService;
import com.updapy.util.MessageUtil;

@Controller
@RequestMapping("user")
public class UserController {

	private final String invalid = "early.interest.add.invalid";
	private final String already = "early.interest.add.already";
	private final String confirm = "early.interest.add.confirm";
	
	@Autowired
	MessageUtil messageUtil;
	
	@Autowired
	private PersonService personService;
	
	@RequestMapping(value="register-early", method=RequestMethod.POST)
	public @ResponseBody String registerEarly(@RequestBody String email) {
		
		if (!EmailValidator.getInstance().isValid(email)) {
			return messageUtil.getSimpleMessage(invalid);
		}
		
		if (personService.findByEmail(email) != null) {
			return messageUtil.getSimpleMessage(already);
		}
		
		personService.registerEarlyWithEmail(email);
		return messageUtil.getSimpleMessage(confirm);
	}
	
	@RequestMapping(value="register", method=RequestMethod.POST)
	public String register() {
		// TODO : check email is valid
		// check password are same
		// check email is not already taken
		// send activation email
		return "sign-up-confirm";
	}
	
	@RequestMapping(value="activate")
	public String sendActivationEmail(@RequestParam(value = "email", required = true) String email) {
		// TODO : check account is not activated
		// send an email
		return "sign-up-confirm-resend";
	}

}
