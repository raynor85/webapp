package com.updapy.service;

import com.updapy.model.User;

public interface UserService {

	User findByEmail(String email);

	User registerEarly(String email);

	User register(User user);

	String generateNewKey(User user);

	User activate(User user);

}
