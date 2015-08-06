package com.updapy.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.updapy.form.model.AddVersion;
import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.service.ApplicationService;

@Component
public class AddVersionCustomValidator implements Validator {

	@Autowired
	private ApplicationService applicationService;

	@Override
	public boolean supports(Class<?> clazz) {
		return AddVersion.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		AddVersion addVersion = (AddVersion) target;

		ApplicationReference application = applicationService.getApplication(addVersion.getApiName().toLowerCase());
		if (application == null) {
			errors.rejectValue("apiName", "Mismatch.addVersion.apiName");
		}

		if (addVersion.getVersionNumber() != null) {
			ApplicationVersion newVersion = new ApplicationVersion();
			newVersion.setVersionNumber(addVersion.getVersionNumber());
			if (!newVersion.isValidVersionNumber()) {
				errors.rejectValue("versionNumber", "Invalid.addVersion.versionNumber");
			} else if (application != null && applicationService.getLatestVersion(application).getFormatedVersionNumber().compareTo(newVersion.getFormatedVersionNumber()) >= 0) {
				errors.rejectValue("versionNumber", "Mismatch.addVersion.versionNumber");
			}
		}
	}

}
