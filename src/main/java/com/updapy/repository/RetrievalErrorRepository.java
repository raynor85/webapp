package com.updapy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.ApplicationReference;
import com.updapy.model.RetrievalError;
import com.updapy.model.enumeration.TypeRetrievalError;

public interface RetrievalErrorRepository extends JpaRepository<RetrievalError, Long> {

	RetrievalError findByApplicationAndTypeLastError(ApplicationReference application, TypeRetrievalError type);

	List<RetrievalError> findByApplicationAndTypeLastErrorIn(ApplicationReference application, List<TypeRetrievalError> types);

	List<RetrievalError> findByCountGreaterThanEqual(int count);

}
