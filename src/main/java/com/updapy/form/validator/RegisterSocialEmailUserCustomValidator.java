package com.updapy.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.updapy.form.model.RegisterSocialEmailUser;
import com.updapy.service.UserService;

@Component
public class RegisterSocialEmailUserCustomValidator implements Validator {

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterSocialEmailUser.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		RegisterSocialEmailUser user = (RegisterSocialEmailUser) target;

		if (userService.findByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "InUse.registerSocialEmailUser.email");
		}
	}

}
