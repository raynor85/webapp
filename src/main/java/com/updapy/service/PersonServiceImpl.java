package com.updapy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.updapy.model.Person;
import com.updapy.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	PersonRepository personRepository;
		
	@Override
	@Transactional
	public void registerEarlyWithEmail(String email) {
		Person person = new Person();
		person.setEarly(true);
		person.setEmail(email);
		personRepository.saveAndFlush(person);
	}

	@Override
	@Transactional
	public List<Person> findByEmail(String email) {
		return personRepository.findByEmail(email);
	}

}
