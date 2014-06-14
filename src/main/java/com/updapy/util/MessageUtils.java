package com.updapy.util;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

@Component
public class MessageUtils {

	@Resource
	private MessageSource messageSource;

	public String getSimpleMessage(String code) {
		return getSimpleMessage(code, LocaleContextHolder.getLocale());
	}

	public String getCustomMessage(String code, Object[] args) {
		return getCustomMessage(code, args, LocaleContextHolder.getLocale());
	}

	public String getSimpleMessage(String code, Locale locale) {
		try {
			return messageSource.getMessage(code, new Object[0], locale);
		} catch (NoSuchMessageException e) {
			return "?" + code + "?";
		}
	}

	public String getCustomMessage(String code, Object[] args, Locale locale) {
		try {
			return messageSource.getMessage(code, args, locale);
		} catch (NoSuchMessageException e) {
			return "?" + code + "?";
		}
	}

	public String getSimpleMessage(ObjectError error) {
		String[] codes = error.getCodes();
		for (String code : codes) {
			try {
				return messageSource.getMessage(code, error.getArguments(), LocaleContextHolder.getLocale());
			} catch (NoSuchMessageException e) {
				// Do nothing
			}
		}
		return "??";
	}
}
