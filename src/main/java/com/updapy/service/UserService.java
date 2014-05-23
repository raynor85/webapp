package com.updapy.service;

import org.springframework.social.connect.Connection;

import com.updapy.model.User;

public interface UserService {

	User getCurrentUser();

	User findByEmail(String email);

	User registerEarly(String email);

	User register(User user);

	User registerSocial(Connection<?> connection);

	String generateNewKey(User user);

	User activate(User user);

	User updatePassword(User user, String newPassword);

	User save(User user);

}
