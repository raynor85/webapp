package com.updapy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.EmailCounter;

public interface EmailCounterRepository extends JpaRepository<EmailCounter, Long> {

}
