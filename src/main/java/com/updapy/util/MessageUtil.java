package com.updapy.util;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

    @Resource
    private MessageSource messageSource;

    public String getSimpleMessage(String code) {
        return messageSource.getMessage(code, new Object[0], LocaleContextHolder.getLocale());
    }
    
    public String getCustomMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
