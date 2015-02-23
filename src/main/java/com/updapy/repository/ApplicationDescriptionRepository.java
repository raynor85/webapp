package com.updapy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.ApplicationDescription;
import com.updapy.model.ApplicationReference;

public interface ApplicationDescriptionRepository extends JpaRepository<ApplicationDescription, Long> {

	List<ApplicationDescription> findByApplicationActiveTrueOrderByApplicationNameAsc();

	ApplicationDescription findByApplicationApiNameAndApplicationActiveTrue(String apiName);

	ApplicationDescription findByApplicationAndApplicationActiveTrue(ApplicationReference application);

}
