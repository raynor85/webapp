package com.updapy.service;

public interface MailSenderService {

	boolean sendActivationLink(String email, String key);

	boolean sendResetPasswordLink(String email, String key);

	boolean sendAdminConnectionError(String url);

	boolean sendAdminRetrieverError(String applicationName);

	boolean sendAdminApplicationRequest(String name, String url);

}
