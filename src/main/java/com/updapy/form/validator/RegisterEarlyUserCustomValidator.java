package com.updapy.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.updapy.form.model.RegisterEarlyUser;
import com.updapy.service.UserService;

@Component
public class RegisterEarlyUserCustomValidator implements Validator {

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterEarlyUser.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		RegisterEarlyUser earlyUser = (RegisterEarlyUser) target;

		if (userService.findByEmail(earlyUser.getEmail()) != null) {
			errors.rejectValue("email", "Already.registerEarlyUser.email");
		}
	}

}
