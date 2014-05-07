package com.updapy.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.updapy.form.model.ResetUser;
import com.updapy.service.UserService;

@Component
public class ResetUserCustomValidator implements Validator {

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return ResetUser.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ResetUser resetUser = (ResetUser) target;

		if (userService.findByEmail(resetUser.getEmail()) == null) {
			errors.rejectValue("email", "NotFound.resetUser.email");
		}
	}

}
