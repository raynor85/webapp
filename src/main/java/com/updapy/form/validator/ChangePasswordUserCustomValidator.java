package com.updapy.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.updapy.form.model.ChangePasswordUser;
import com.updapy.service.UserService;

@Component
public class ChangePasswordUserCustomValidator implements Validator {

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return ChangePasswordUser.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ChangePasswordUser changePasswordUser = (ChangePasswordUser) target;

		if (!(userService.isCurrentPassword(changePasswordUser.getCurrentPassword()))) {
			errors.rejectValue("currentPassword", "Invalid.changePasswordUser.currentPassword");
		}
		if (!(changePasswordUser.getNewPassword().equals(changePasswordUser.getRepeatNewPassword()))) {
			errors.rejectValue("newPassword", "Mismatch.changePasswordUser.newPassword");
		}

	}

}
