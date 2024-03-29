package com.updapy.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.ApplicationReference;

public interface ApplicationReferenceRepository extends JpaRepository<ApplicationReference, Long> {

	List<ApplicationReference> findByActiveTrue();

	List<ApplicationReference> findByActiveTrueAndIgnoranceTypeNotNull();

	List<ApplicationReference> findByNotifiedTrueAndActiveTrueOrderByApiNameAsc();

	ApplicationReference findByApiNameAndNotifiedTrueAndActiveTrue(String apiName);

	List<ApplicationReference> findByNotifiedFalseAndActiveTrue();

	List<ApplicationReference> findByNotifiedFalseAndActiveFalse();

	List<ApplicationReference> findByActiveTrueOrderByCreationDateDesc(Pageable pageable);

	Long countByActiveTrue();

}
