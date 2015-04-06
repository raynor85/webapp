package com.updapy.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.updapy.model.ApplicationReference;
import com.updapy.model.RetrievalError;
import com.updapy.model.enumeration.TypeRetrievalError;

@Transactional
public interface RetrievalErrorService {

	List<RetrievalError> getAllRetrievalErrors();

	RetrievalError addRetrievalError(ApplicationReference application, TypeRetrievalError type);

	RetrievalError addRetrievalError(ApplicationReference application, TypeRetrievalError type, String message);

	void deleteRetrievalError(long id);

	void deleteRetrievalErrors(ApplicationReference application, List<TypeRetrievalError> types);

	int sendEmailRetrievalErrors();

}
