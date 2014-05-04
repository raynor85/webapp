package com.updapy.util;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

@Component
public class MessageUtil {

	@Resource
	private MessageSource messageSource;

	public String getSimpleMessage(String code) {
		try {
			return messageSource.getMessage(code, new Object[0], LocaleContextHolder.getLocale());
		} catch (NoSuchMessageException e) {
			return "?" + code + "?";
		}
	}

	public String getCustomMessage(String code, Object[] args) {
		try {
			return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
		} catch (NoSuchMessageException e) {
			return "?" + code + "?";
		}
	}

	public String getSimpleMessage(ObjectError error) {
		String[] codes = error.getCodes();
		for (String code : codes) {
			try {
				return messageSource.getMessage(code, new Object[0], LocaleContextHolder.getLocale());
			} catch (NoSuchMessageException e) {
				// Do nothing
			}
		}
		return "??";
	}
}
