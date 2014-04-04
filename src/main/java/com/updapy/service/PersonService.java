package com.updapy.service;

import java.util.List;

import com.updapy.model.Person;


public interface PersonService {

	void registerEarlyWithEmail(String email);
	List<Person> findByEmail(String email);
}
