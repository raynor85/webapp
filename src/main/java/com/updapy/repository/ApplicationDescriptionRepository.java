package com.updapy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.ApplicationDescription;

public interface ApplicationDescriptionRepository extends JpaRepository<ApplicationDescription, Long> {

	List<ApplicationDescription> findByApplicationActiveTrueOrderByApplicationNameAsc();

}
