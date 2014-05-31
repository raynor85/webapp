package com.updapy.service;

import org.springframework.social.connect.Connection;
import org.springframework.transaction.annotation.Transactional;

import com.updapy.model.User;

@Transactional
public interface UserService {

	User getCurrentUser();

	User findByEmail(String email);

	User registerEarly(String email);

	User register(User user);

	User registerSocial(Connection<?> connection);

	String generateNewAccountKey(User user);

	User activate(User user);

	User updatePassword(User user, String newPassword);

	User updateCurrentPassword(String newPassword);

	User save(User user);

	boolean isCurrentPassword(String password);

	boolean delete(User user);

	boolean deleteCurrentUser();

}
