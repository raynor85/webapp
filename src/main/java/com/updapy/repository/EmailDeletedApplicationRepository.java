package com.updapy.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.EmailDeletedApplication;

public interface EmailDeletedApplicationRepository extends JpaRepository<EmailDeletedApplication, Long> {

	List<EmailDeletedApplication> findBySentFalse(Pageable pageable);

}
