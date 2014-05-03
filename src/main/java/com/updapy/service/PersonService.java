package com.updapy.service;

import com.updapy.model.Person;


public interface PersonService {

	void registerEarlyWithEmail(String email);
	Person findByEmail(String email);
}
