package com.updapy.form.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.updapy.form.model.RequestApplication;
import com.updapy.service.ApplicationService;

@Component
public class RequestApplicationCustomValidator implements Validator {

	@Autowired
	private ApplicationService applicationService;

	@Override
	public boolean supports(Class<?> clazz) {
		return RequestApplication.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		RequestApplication application = (RequestApplication) target;

		if (applicationService.getApplication(transform(application.getName())) != null) {
			errors.rejectValue("name", "Already.requestApplication.name");
		}
	}

	private String transform(String name) {
		return StringUtils.lowerCase(StringUtils.remove(StringUtils.remove(StringUtils.deleteWhitespace(name), '.'), '-'));
	}

}
