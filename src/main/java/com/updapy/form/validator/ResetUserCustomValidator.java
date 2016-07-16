package com.updapy.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.updapy.form.model.ResetUser;

@Component
public class ResetUserCustomValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ResetUser.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ResetUser resetUser = (ResetUser) target;

		if (!(resetUser.getNewPassword().equals(resetUser.getRepeatNewPassword()))) {
			errors.rejectValue("newPassword", "Mismatch.resetUser.newPassword");
		}

	}

}
