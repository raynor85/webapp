package com.updapy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.ApplicationReference;
import com.updapy.model.RetrievalError;

public interface RetrievalErrorRepository extends JpaRepository<RetrievalError, Long> {

	RetrievalError findByApplication(ApplicationReference application);

	List<RetrievalError> findByCountGreaterThanEqual(int count);

}
