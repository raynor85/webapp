package com.updapy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.ApplicationRequest;

public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest, Long> {

}
