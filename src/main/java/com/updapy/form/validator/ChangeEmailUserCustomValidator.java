package com.updapy.form.validator;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.updapy.form.model.ChangeEmailUser;
import com.updapy.service.UserService;

@Component
public class ChangeEmailUserCustomValidator implements Validator {

	@Inject
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return ChangeEmailUser.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ChangeEmailUser user = (ChangeEmailUser) target;

		if (userService.findByEmail(user.getNewEmail()) != null) {
			errors.rejectValue("newEmail", "InUse.changeEmailUser.newEmail");
		}
		if (!(user.getNewEmail().equals(user.getRepeatNewEmail()))) {
			errors.rejectValue("newEmail", "Mismatch.changeEmailUser.newEmail");
		}
	}

}
