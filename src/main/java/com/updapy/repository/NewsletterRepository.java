package com.updapy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.Newsletter;

public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {

	List<Newsletter> findByNotifiedFalseAndActiveTrue();

}
