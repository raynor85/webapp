package com.updapy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;

public interface ApplicationVersionRepository extends JpaRepository<ApplicationVersion, Long> {

	@Query("select v from ApplicationVersion v where v.reference = :reference and v.versionDate = (select max(v.versionDate) from ApplicationVersion v where v.reference = :reference))")
	ApplicationVersion findLatestByApplicationReference(@Param("reference") ApplicationReference applicationReference);

}
