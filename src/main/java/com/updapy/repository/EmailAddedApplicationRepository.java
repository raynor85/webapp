package com.updapy.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.EmailAddedApplication;

public interface EmailAddedApplicationRepository extends JpaRepository<EmailAddedApplication, Long> {

	List<EmailAddedApplication> findBySentFalse(Pageable pageable);

}
