package com.updapy.form.validator;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.updapy.form.model.ResetUserEmail;
import com.updapy.service.UserService;

@Component
public class ResetUserEmailCustomValidator implements Validator {

	@Inject
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return ResetUserEmail.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ResetUserEmail resetUserEmail = (ResetUserEmail) target;

		if (userService.findByEmail(resetUserEmail.getEmail()) == null) {
			errors.rejectValue("email", "NotFound.resetUserEmail.email");
		}
	}

}
