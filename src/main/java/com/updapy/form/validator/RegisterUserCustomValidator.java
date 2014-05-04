package com.updapy.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.updapy.form.model.RegisterUser;
import com.updapy.service.UserService;

@Component
public class RegisterUserCustomValidator implements Validator {

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterUser.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		RegisterUser user = (RegisterUser) target;

		if (!(user.getPassword().equals(user.getRepeatPassword()))) {
			errors.rejectValue("password", "Mismatch.registerUser.password");
		}

		if (userService.findByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "InUse.registerUser.email");
		}
	}

}
