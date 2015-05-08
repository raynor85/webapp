package com.updapy.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.ApplicationRequest;

public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest, Long> {

	List<ApplicationRequest> findByOrderByCreationDateDesc(Pageable pageable);

}
