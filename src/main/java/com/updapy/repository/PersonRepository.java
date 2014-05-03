package com.updapy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
	
	Person findByEmail(String email);
}
