package com.updapy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
	
	List<Person> findByEmail(String email);
}
