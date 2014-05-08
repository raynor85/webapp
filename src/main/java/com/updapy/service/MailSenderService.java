package com.updapy.service;

public interface MailSenderService {

	boolean sendActivationLink(String email, String key);

	boolean sendResetPasswordLink(String email, String key);

}
