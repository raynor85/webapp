package com.updapy.service;

import com.updapy.model.User;

public interface UserService {

	User findByEmail(String email);

	void registerEarly(String email);

}
